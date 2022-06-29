package vald3nir.android_snmp.agent.controller;


import android.content.Context;

import static vald3nir.manager_snmp.core.MIBs.MIB_DEVICE_MODEL;
import static vald3nir.manager_snmp.core.MIBs.MIB_NIVEL_BATTERY;
import static vald3nir.manager_snmp.core.MIBs.MIB_STATUS_BATTERY;
import static vald3nir.manager_snmp.core.MIBs.MIB_STATUS_BLUETOOTH;
import static vald3nir.manager_snmp.core.MIBs.MIB_STATUS_GPS;
import static vald3nir.manager_snmp.core.MIBs.MIB_STATUS_WIFI;
import static vald3nir.manager_snmp.core.MIBs.MIB_VERSION_SYSTEM_OPERATION;


class MIBtree {

    String getData(String oid, Context context) throws Exception {

        switch (oid) {

            case MIB_DEVICE_MODEL:
                return SystemCallsManager.getDeviceModel();

            case MIB_VERSION_SYSTEM_OPERATION:
                return SystemCallsManager.getOSVersion();

            case MIB_STATUS_BATTERY:
                return SystemCallsManager.getStatusBattery(context);

            case MIB_NIVEL_BATTERY:
                return SystemCallsManager.getPercentageBattery(context);

            case MIB_STATUS_GPS:
                return SystemCallsManager.getStatusGPS(context);

            case MIB_STATUS_BLUETOOTH:
                return SystemCallsManager.getStatusBluetooth();

            case MIB_STATUS_WIFI:
                return SystemCallsManager.getStatusWIFI(context);

            default:
                throw new Exception();
        }
    }

    String getResponseToTrap(Context context) {
        return SystemCallsManager.getPercentageBattery(context);
    }

}
