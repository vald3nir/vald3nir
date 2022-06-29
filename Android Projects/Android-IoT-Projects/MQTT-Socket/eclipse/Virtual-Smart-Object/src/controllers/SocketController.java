package controllers;

import config.AppsConfig;
import smart_object.SmartObjectDelegate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketController {

    private ISocketController callback;
    private String topicPublish, topicSubscriber;
    private SmartObjectDelegate smartObjectDelegate;

    public SocketController(SmartObjectDelegate smartObjectDelegate, String topicPublish, String topicSubscriber) {
        this.topicSubscriber = topicSubscriber;
        this.topicPublish = topicPublish;
        this.smartObjectDelegate = smartObjectDelegate;
    }

    public void setCallback(ISocketController callback) {
        this.callback = callback;
    }

    private Runnable subscriberTask = new Runnable() {
        @Override
        public void run() {

            try {

                // SUBSCRIBER
                // =======================================================================================
                Socket socket = new Socket(AppsConfig.ADDRESS_GATEWAY_SOCKET, AppsConfig.PORT_GATEWAY_SOCKET);

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write(AppsConfig.createMessageToSubscriber(topicSubscriber, true));
                bufferedWriter.flush();

                socket.close();
                // =======================================================================================

                @SuppressWarnings("resource")
                ServerSocket serverSocket = new ServerSocket(AppsConfig.PORT_DEFAULT);
                while (true) {

                    socket = serverSocket.accept();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String response = bufferedReader.readLine().split(AppsConfig.DIVIDER)[1];
                    socket.close();

                    smartObjectDelegate.enableSocketPublish(response);

                    if (callback != null) {
                        callback.onResponse(response);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable publishTask = new Runnable() {
        @Override
        public void run() {

            Socket socket;
            BufferedWriter bufferedWriter;

            while (true) {

                try {

                    if (smartObjectDelegate.isEnableSocket()) {

                        socket = new Socket(AppsConfig.ADDRESS_GATEWAY_SOCKET, AppsConfig.PORT_GATEWAY_SOCKET);

                        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        bufferedWriter.write(AppsConfig.createMessageToPublish(topicPublish, true));
                        bufferedWriter.flush();

                        socket.close();
                    }

                    Thread.sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void connect() {
        new Thread(subscriberTask).start();
        new Thread(publishTask).start();
    }

    public interface ISocketController {

        void onResponse(String response);
    }

}
