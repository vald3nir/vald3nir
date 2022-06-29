package com.vald3nir.sd.controllers;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static com.vald3nir.sd.AppsConfig.ACTION_PUBLISH;
import static com.vald3nir.sd.AppsConfig.ADDRESS_GATEWAY_SOCKET;
import static com.vald3nir.sd.AppsConfig.DIVIDER;
import static com.vald3nir.sd.AppsConfig.PORT_GATEWAY_SOCKET;

public class SocketPublishTask extends AsyncTask<Void, Void, String> {
    private String message;
    private String topicPublish;

    SocketPublishTask(String message, String topicPublish) {
        this.message = message;
        this.topicPublish = topicPublish;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {

            Socket socket = new Socket(ADDRESS_GATEWAY_SOCKET, PORT_GATEWAY_SOCKET);

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(ACTION_PUBLISH + DIVIDER + topicPublish + DIVIDER + message + "\n");
            bufferedWriter.flush();

            socket.close();
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}