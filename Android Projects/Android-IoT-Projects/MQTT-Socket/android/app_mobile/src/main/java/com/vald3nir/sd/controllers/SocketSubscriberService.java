package com.vald3nir.sd.controllers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static com.vald3nir.sd.AppsConfig.ACTION_SUBSCRIBER;
import static com.vald3nir.sd.AppsConfig.ADDRESS_GATEWAY_SOCKET;
import static com.vald3nir.sd.AppsConfig.DIVIDER;
import static com.vald3nir.sd.AppsConfig.PORT_DEFAULT;
import static com.vald3nir.sd.AppsConfig.PORT_GATEWAY_SOCKET;
import static com.vald3nir.sd.AppsConfig.TOPIC_VALUE_AIR;
import static com.vald3nir.sd.AppsConfig.TOPIC_VALUE_ALARM;
import static com.vald3nir.sd.AppsConfig.TOPIC_VALUE_LIGHT;
import static com.vald3nir.sd.AppsConfig.TOPIC_VALUE_PC;
import static com.vald3nir.sd.AppsConfig.TOPIC_VALUE_TV;

public class SocketSubscriberService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        callSubscriber();
    }

    private void sendMyBroadCast(String response) {
        try {

            Intent intent = new Intent();

            intent.setAction(SubscriberReceiver.BROADCAST_ACTION);
            intent.putExtra(SubscriberReceiver.DATA_PARAM, response);

            sendBroadcast(intent);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void callSubscriber() {

        new Thread(() -> {

            Socket socket;
            BufferedWriter bufferedWriter;
            BufferedReader bufferedReader;
            ServerSocket serverSocket;

            try {

                // SUBSCRIBER
                // =======================================================================================
                socket = new Socket(ADDRESS_GATEWAY_SOCKET, PORT_GATEWAY_SOCKET);

                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write(ACTION_SUBSCRIBER + DIVIDER + TOPIC_VALUE_LIGHT + DIVIDER + "OK\n");
                bufferedWriter.write(ACTION_SUBSCRIBER + DIVIDER + TOPIC_VALUE_AIR + DIVIDER + "OK\n");
                bufferedWriter.write(ACTION_SUBSCRIBER + DIVIDER + TOPIC_VALUE_TV + DIVIDER + "OK\n");
                bufferedWriter.write(ACTION_SUBSCRIBER + DIVIDER + TOPIC_VALUE_ALARM + DIVIDER + "OK\n");
                bufferedWriter.write(ACTION_SUBSCRIBER + DIVIDER + TOPIC_VALUE_PC + DIVIDER + "OK\n");
                bufferedWriter.flush();

                socket.close();
                // =======================================================================================

                serverSocket = new ServerSocket(PORT_DEFAULT);

                while (true) try {

                    socket = serverSocket.accept();

                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String response = bufferedReader.readLine();
                    socket.close();

                    sendMyBroadCast(response);
                    Thread.sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }).start();
    }
}