package vald3nir.manager_snmp.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import vald3nir.manager_snmp.core.MIBs;

import static vald3nir.manager_snmp.core.MIBs.NO_ERROR;
import static vald3nir.manager_snmp.core.MIBs.PORT_SNMP;
import static vald3nir.manager_snmp.core.MIBs.SEPARATOR;
import static vald3nir.manager_snmp.core.MIBs.SET_REQUEST;


class ManagerHandler {

    private final JTextArea log;
    private final JTextField community;

    private JTextField ip = new JTextField();
    private JTextField port = new JTextField();

    // =============================================================================================

    private void getInforConection() {
        ip = new JTextField("192.168.0.13");
        port = new JTextField(PORT_SNMP.toString());
        JOptionPane.showMessageDialog(null, new Object[]{"IP do Agente:", ip, "Porta SNMP:", port});
    }

    // =============================================================================================

    ManagerHandler(JTextArea log, JTextField community) {
        this.log = log;
        this.community = community;
    }

    // =============================================================================================

    private void saveLog(String msg, String response) {
        log.append("\nGerente:\n" + msg + "\nAgente:\n" + response + "\n");
    }

    // =============================================================================================

    private int requestId = 1;

    private int requestID() {
        int id = requestId;
        requestId++;
        return id;
    }

    // =============================================================================================

    public void setRequest(String oid, Object value) {

        StringBuilder msg = new StringBuilder();

        msg.append(MIBs.VERSION_SNMP);
        msg.append(SEPARATOR);

        msg.append(this.community.getText());
        msg.append(SEPARATOR);

        msg.append(SET_REQUEST);
        msg.append(SEPARATOR);

        msg.append(requestId);
        msg.append(SEPARATOR);

        msg.append(NO_ERROR); // errorStatus
        msg.append(SEPARATOR);
        msg.append(NO_ERROR); // errorIndex
        msg.append(SEPARATOR);

        msg.append(oid);
        msg.append(SEPARATOR);
        msg.append(value); // errorIndex

        msg.append("\r\n");

        getInforConection();
        String response = sendMessage(ip.getText(), Integer.parseInt(port.getText()), msg.toString());

        saveLog(msg.toString(), response);

    }


    // =============================================================================================

    public void getRequest(ArrayList<String> oids) {

        String msg = generateMessage(oids, MIBs.GET_REQUEST, requestID());

        getInforConection();

        String response = sendMessage(ip.getText(), Integer.parseInt(port.getText()), msg);

        saveLog(msg, response);
    }

    public void getNextRequest() {

        String msg = generateMessage(null, MIBs.GET_NEXT_REQUEST, requestID());

        getInforConection();

        String response = sendMessage(ip.getText(), Integer.parseInt(port.getText()), msg);

        saveLog(msg, response);
    }

    // =============================================================================================

    private String generateMessage(ArrayList<String> oids, String typeRequest, int requestId) {

        StringBuilder msg = new StringBuilder();

        msg.append(MIBs.VERSION_SNMP);
        msg.append(SEPARATOR);

        msg.append(this.community.getText());
        msg.append(SEPARATOR);

        msg.append(typeRequest);
        msg.append(SEPARATOR);

        msg.append(requestId);
        msg.append(SEPARATOR);

        msg.append(NO_ERROR); // errorStatus
        msg.append(SEPARATOR);
        msg.append(NO_ERROR); // errorIndex

        if (oids != null) for (String oid : oids) {
            msg.append(SEPARATOR);
            msg.append(oid);
            msg.append(SEPARATOR);
            msg.append("null");
        }

        msg.append("\r\n");
        return msg.toString();
    }

    // =============================================================================================

    private String sendMessage(String ip, Integer port, String message) {

        try {

            InetAddress inteAddress = InetAddress.getByName(ip);
            SocketAddress socketAddress = new InetSocketAddress(inteAddress, port);

            Socket socket = new Socket();

            int timeoutInMs = 10 * 1000;   // 10 segundos
            socket.connect(socketAddress, timeoutInMs);

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(message);
            bufferedWriter.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = bufferedReader.readLine();

            bufferedReader.close();
            socket.close();

            return response;

        } catch (Exception e) {
            return e.getMessage();
        }
    }


}