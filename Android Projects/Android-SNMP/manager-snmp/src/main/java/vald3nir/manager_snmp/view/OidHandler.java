package vald3nir.manager_snmp.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import vald3nir.manager_snmp.core.MIBs;
import vald3nir.manager_snmp.core.OID;

import static vald3nir.manager_snmp.core.MIBs.MIB_DEVICE_MODEL;
import static vald3nir.manager_snmp.core.MIBs.MIB_NIVEL_BATTERY;
import static vald3nir.manager_snmp.core.MIBs.MIB_STATUS_BATTERY;
import static vald3nir.manager_snmp.core.MIBs.MIB_STATUS_BLUETOOTH;
import static vald3nir.manager_snmp.core.MIBs.MIB_STATUS_GPS;
import static vald3nir.manager_snmp.core.MIBs.MIB_STATUS_WIFI;
import static vald3nir.manager_snmp.core.MIBs.MIB_VERSION_SYSTEM_OPERATION;


class OidHandler {

    private final ArrayList<OID> oids = new ArrayList<>();

    private final ArrayList<String> oidsSelected = new ArrayList<>();

    OidHandler() {
        oids.add(new OID(MIB_DEVICE_MODEL, "Modelo do Dispositivo"));
        oids.add(new OID(MIB_VERSION_SYSTEM_OPERATION, "Versão do Sistema Operacional"));
        oids.add(new OID(MIB_STATUS_BATTERY, "Status da Bateria"));
        oids.add(new OID(MIB_NIVEL_BATTERY, "Nível da Bateria"));
        oids.add(new OID(MIB_STATUS_GPS, "Status do GPS"));
        oids.add(new OID(MIB_STATUS_BLUETOOTH, "Status do Bluetooth"));
        oids.add(new OID(MIB_STATUS_WIFI, "Status do WIFI"));
    }


    void createPanelOids(JPanel panelListMibs) {

        panelListMibs.setLayout(new GridLayout(this.oids.size(), 1, 0, 0));

        for (final OID oid : this.oids) {

            final JRadioButton jRadioButton = new JRadioButton(oid.getDescription());
            panelListMibs.add(jRadioButton);

            jRadioButton.setHorizontalAlignment(SwingConstants.LEFT);
            jRadioButton.setBackground(Color.WHITE);
            jRadioButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent arg0) {
                    if (jRadioButton.isSelected()) {
                        oidsSelected.add(oid.getOID());
                    } else {
                        oidsSelected.remove(oid.getOID());
                    }
                }
            });
        }
    }

    ArrayList<String> getOidsSelected() {
        return oidsSelected;
    }

    String getOIDEnableOrDesableTrap() {
        return MIBs.MIB_TRAP_ANDROID;
    }
}
