package vald3nir.android_snmp.agent.controller;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class SystemCallsManager {

    public static String getCurrentIP() {

        try {

            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface networkInterface : interfaces) {
                List<InetAddress> inetAddresses = Collections.list(networkInterface.getInetAddresses());

                for (InetAddress inetAddress : inetAddresses) {

                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = inetAddress.getHostAddress();

                        if (ip.indexOf(':') < 0)
                            return ip;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    // =============================================================================================

    public static String getDeviceModel() {
        return formatResponse(Build.MANUFACTURER + " " + Build.MODEL);
    }

    // =============================================================================================

    public static String getOSVersion() {
        return formatResponse(Build.VERSION.RELEASE);
    }

    // =============================================================================================

    public static String getStatusBattery(Context context) {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int status = 0;

        if (batteryStatus != null) {
            status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        }

        if (status == BatteryManager.BATTERY_STATUS_CHARGING)
            return formatResponse("Carregando");

        if (status == BatteryManager.BATTERY_STATUS_FULL)
            return formatResponse("Carregado");

        return formatResponse("Descarregando");
    }

    // =============================================================================================

    public static String getPercentageBattery(Context context) {
        return formatResponse(getLevelBattery(context) + " %");
    }

    private static Float getLevelBattery(Context context) {

        Intent batteryStatus = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        int level = 0;
        int scale = 0;

        if (batteryStatus != null) {
            level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        }

        return 100 * level / (float) scale;
    }

    // =============================================================================================

    public static String getStatusGPS(Context context) {

        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        boolean isOn = false;

        if (manager != null) {
            isOn = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

        return formatResponse(isOn ? "Ligado" : "desligado");
    }

    // =============================================================================================

    public static String getStatusBluetooth() {

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        boolean isOn = bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON || bluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_ON;

        return formatResponse(isOn ? "Ligado" : "Desligado");
    }

    // =============================================================================================

    public static String getStatusWIFI(Context context) {

        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        boolean isOn = false;

        if (wifiManager != null) {
            isOn = wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED || wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING;
        }

        return formatResponse(isOn ? "Ligado" : "Desligado");
    }

    // =============================================================================================

    private static String formatResponse(String response) {
        return "[" + response + "]";
    }

}