package controllers;

import config.AppsConfig;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import smart_object.SmartObjectDelegate;


public class MQTTController {

    private SmartObjectDelegate smartObjectDelegate;
    private MqttClient sampleClient;
    private String topicPublish, topicSubscriber;

    public MQTTController(SmartObjectDelegate smartObjectDelegate, String topicPublish, String topicSubscriber) {
        this.topicPublish = topicPublish;
        this.topicSubscriber = topicSubscriber;
        this.smartObjectDelegate = smartObjectDelegate;
    }

    public void connect(String clientId) throws MqttException {

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);

        sampleClient = new MqttClient(AppsConfig.ADDRESS_BROKEN_MQTT, clientId, new MemoryPersistence());
        sampleClient.connect(mqttConnectOptions);

        new Thread(publishTask).start();
    }

    public void subscribe(IMQTTController callback) throws MqttException {
        if (sampleClient != null) {
            sampleClient.subscribe(topicSubscriber, (topic, message) -> {
                if (topicSubscriber.equalsIgnoreCase(topic)) {
                    String response = new String(message.getPayload());
                    smartObjectDelegate.enableMQTTPublish(response);
                    callback.onResponse(response);
                }
            });
        }
    }

    private Runnable publishTask = new Runnable() {
        @Override
        public void run() {

            MqttMessage mqttMessage = new MqttMessage(AppsConfig.createMessageToPublish(topicPublish, false).getBytes());
            mqttMessage.setQos(0);

            while (true) {

                try {

                    if (smartObjectDelegate.isEnableMQTT()) {
                        sampleClient.publish(topicPublish, mqttMessage);
                    }

                    Thread.sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public interface IMQTTController {

        void onResponse(String response);

    }
}
