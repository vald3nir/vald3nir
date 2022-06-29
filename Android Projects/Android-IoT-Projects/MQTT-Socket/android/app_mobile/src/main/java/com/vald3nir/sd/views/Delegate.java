package com.vald3nir.sd.views;

import com.vald3nir.sd.controllers.MQTTController;
import com.vald3nir.sd.controllers.SocketController;


class Delegate {

    private final SocketController socketController;
    private final MQTTController mqttController;

    Delegate() {
        socketController = new SocketController();
        mqttController = new MQTTController();
    }

    void publishMessageSocket(MainActivity activity, String topic, String message) {
        activity.writeLog("Android sent to topic: " + topic + " = " + message);
        socketController.publish(topic, message);
    }

    void publishMessageMQTT(MainActivity activity, String topic, String message) {
        activity.writeLog("Android sent to topic: " + topic + " = " + message);
        mqttController.publish(topic, message);
    }

    public void startServices(MainActivity activity) {
        mqttController.connect(activity);
        socketController.subscriber(activity);
    }

    public void stopServices(MainActivity activity) {
        socketController.stopSubscriber(activity);
    }
}
