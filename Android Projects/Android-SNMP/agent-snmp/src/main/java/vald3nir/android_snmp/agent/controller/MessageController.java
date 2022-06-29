package vald3nir.android_snmp.agent.controller;

import android.annotation.SuppressLint;
import android.content.Context;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import vald3nir.android_snmp.agent.view.AgentDelegate;

import static vald3nir.manager_snmp.core.MIBs.COMMUNITY;
import static vald3nir.manager_snmp.core.MIBs.GEN_ERR;
import static vald3nir.manager_snmp.core.MIBs.GET_NEXT_REQUEST;
import static vald3nir.manager_snmp.core.MIBs.GET_REQUEST;
import static vald3nir.manager_snmp.core.MIBs.GET_RESPONSE;
import static vald3nir.manager_snmp.core.MIBs.MIB_TRAP_ANDROID;
import static vald3nir.manager_snmp.core.MIBs.NO_ERROR;
import static vald3nir.manager_snmp.core.MIBs.NO_SUCH_NAME;
import static vald3nir.manager_snmp.core.MIBs.SEPARATOR;
import static vald3nir.manager_snmp.core.MIBs.SET_REQUEST;
import static vald3nir.manager_snmp.core.MIBs.TRAP;
import static vald3nir.manager_snmp.core.MIBs.VERSION_SNMP;

public class MessageController {

    private AgentDelegate delegate;
    private Context context;
    private MIBtree mibtree;
    private boolean flagGetNextValid = false;
    private String lastOID;

    //==============================================================================================

    public MessageController(AgentDelegate delegate, Context context) {
        this.context = context;
        this.delegate = delegate;
        this.mibtree = new MIBtree();
    }

    //==============================================================================================

    public String generateGetResponse(String message, Socket socket) {

        String[] fields = message.split(SEPARATOR);

        if (validateHeader(fields)) {

            switch (fields[2]) {

                case GET_REQUEST:

                    String errIndex = "";

                    //==============================================================================

                    if (fields.length == 8) { // verifica se foi passado mais de uma oid
                        flagGetNextValid = true;
                        lastOID = fields[6];

                    } else {
                        flagGetNextValid = false;
                    }

                    //==============================================================================

                    for (int i = 6; i < fields.length; i += 2)
                        try {

                            // valor                          oid
                            fields[i + 1] = mibtree.getData(fields[i], context);

                        } catch (Exception e) {
                            fields[4] = NO_SUCH_NAME;
                            fields[5] += errIndex + i + "-";
                        }

                    fields[2] = GET_RESPONSE;

                    return arrayToString(fields);

                //==================================================================================

                case GET_NEXT_REQUEST:

                    String errorStatus = NO_ERROR;
                    String errorIndex = NO_ERROR;
                    String response = "null";

                    if (flagGetNextValid) {

                        try {

                            lastOID = getNextOID(lastOID);
                            response = mibtree.getData(lastOID, context);

                        } catch (Exception e) {
                            errorStatus = NO_SUCH_NAME;
                            errorIndex = "7"; // erro no campo referente a oid
                        }

                    } else {
                        errorStatus = GEN_ERR;
                        errorIndex = "7"; // erro no campo referente a oid
                    }

                    return generateGetNextResponse(fields[3], errorStatus, errorIndex, response);

                //==================================================================================

                case SET_REQUEST:

                    if (fields[7].equalsIgnoreCase("true")) { // trap ativada

                        delegate.ipManager = socket.getInetAddress().getHostAddress();
                        delegate.messageTrap = generateTrap(mibtree.getResponseToTrap(context));
                        delegate.enableTrap = true;

                    } else {

                        delegate.ipManager = null;
                        delegate.messageTrap = null;
                        delegate.enableTrap = false;

                    }

                    break;
            }
        }

        fields[2] = GET_RESPONSE;
        return arrayToString(fields);

    }

    // =============================================================================================

    private boolean validateHeader(String[] fields) {

        boolean err = false;
        String errIndex = "";

        if (!fields[0].equalsIgnoreCase(VERSION_SNMP)) {

            errIndex += "0";

            fields[4] = GEN_ERR;
            fields[5] = errIndex;

            err = true;
        }

        if (!fields[1].equalsIgnoreCase(COMMUNITY)) {

            errIndex += "1";

            fields[4] = GEN_ERR;
            fields[5] = errIndex;

            err = true;
        }

        return !err;
    }

    // =============================================================================================

    private String arrayToString(String[] array) {

        StringBuilder msg = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            msg.append(array[i]);
            if (i < array.length - 1)
                msg.append(SEPARATOR);
        }

        msg.append("\r\n");
        return msg.toString();
    }

    // =============================================================================================

    private String getNextOID(String oid) {

        String[] fields = oid.split("\\.");

        int index = fields.length - 1;
        fields[index] = (Integer.parseInt(fields[index]) + 1) + ""; // soma mais 1 no ultimo indice da oid

        StringBuilder s = new StringBuilder();

        for (int i = 0; i < fields.length; i++) {
            s.append(fields[i]);
            if (i < fields.length - 1)
                s.append(".");
        }

        return s.toString();
    }

    // =============================================================================================

    private String generateGetNextResponse(String requestId, String errorStatus, String errorIndex, String response) {

        return VERSION_SNMP + SEPARATOR +
                COMMUNITY + SEPARATOR +
                GET_RESPONSE + SEPARATOR +
                requestId + SEPARATOR +
                errorStatus + SEPARATOR +
                errorIndex + SEPARATOR +
                lastOID + SEPARATOR +
                response + "\r\n\n";
    }

    // =============================================================================================

    @SuppressLint("SimpleDateFormat")
    private String generateTrap(String value) {

        return VERSION_SNMP + SEPARATOR +
                COMMUNITY + SEPARATOR +
                TRAP + SEPARATOR +
                0 + SEPARATOR +// enterprise
                SystemCallsManager.getCurrentIP() + SEPARATOR +
                0 + SEPARATOR + // generic-trap
                0 + SEPARATOR + // specific-trap
                new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) +
                SEPARATOR + MIB_TRAP_ANDROID + SEPARATOR + value + "\r\n";
    }

}