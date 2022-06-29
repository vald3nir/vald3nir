package smart_object;

import controllers.MQTTController;
import controllers.SocketController;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class SmartObjectDelegate {

    private SocketController socketController;
    private MQTTController mqttController;
    private boolean enableMQTT, enableSocket;

    SmartObjectDelegate(String topicPublish, String topicSubscriber) {
        socketController = new SocketController(this, topicPublish, topicSubscriber);
        mqttController = new MQTTController(this, topicPublish, topicSubscriber);
        enableMQTT = false;
        enableSocket = false;
    }

    void startServices(SmartObject smartObject, String name) throws Exception {
        socketController.connect();
        socketController.setCallback(new SocketController.ISocketController() {
            @Override
            public void onResponse(String response) {
                smartObject.setStatus(response);
            }
        });

        mqttController.connect(name);
        mqttController.subscribe(new MQTTController.IMQTTController() {
            @Override
            public void onResponse(String response) {
                smartObject.setStatus(response);
            }
        });
    }

    String getIP() {

        String ip = "x.x.x.x";

        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ip;

    }

    public void enableMQTTPublish(String response) {
        enableMQTT = response.equalsIgnoreCase("on");
        enableSocket = false;
    }

    public void enableSocketPublish(String response) {
        enableSocket = response.equalsIgnoreCase("on");
        enableMQTT = false;
    }

    public boolean isEnableMQTT() {
        return enableMQTT;
    }

    public boolean isEnableSocket() {
        return enableSocket;
    }

}
