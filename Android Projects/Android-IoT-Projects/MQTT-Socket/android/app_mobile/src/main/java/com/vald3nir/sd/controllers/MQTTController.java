package com.vald3nir.sd.controllers;

import com.vald3nir.sd.views.MainActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.vald3nir.sd.AppsConfig.ADDRESS_BROKEN_MQTT;
import static com.vald3nir.sd.AppsConfig.TOPIC_VALUE_AIR;
import static com.vald3nir.sd.AppsConfig.TOPIC_VALUE_ALARM;
import static com.vald3nir.sd.AppsConfig.TOPIC_VALUE_LIGHT;
import static com.vald3nir.sd.AppsConfig.TOPIC_VALUE_PC;
import static com.vald3nir.sd.AppsConfig.TOPIC_VALUE_TV;


public class MQTTController {

    private MqttAndroidClient mqttClient;

    public void connect(MainActivity activity) {

        this.mqttClient = new MqttAndroidClient(activity, ADDRESS_BROKEN_MQTT, "android");

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        this.mqttClient.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
            }

            @Override
            public void connectionLost(Throwable throwable) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage messageMQTT) {
                activity.writeLog(new String(messageMQTT.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });


        try {

            mqttClient.connect(mqttConnectOptions, null, new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);

                    mqttClient.setBufferOpts(disconnectedBufferOptions);

                    subscriber(activity);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable throwable) {
                    throwable.printStackTrace();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void subscriber(MainActivity activity) {

        String[] topics = {TOPIC_VALUE_LIGHT, TOPIC_VALUE_AIR, TOPIC_VALUE_TV, TOPIC_VALUE_ALARM, TOPIC_VALUE_PC};
        int[] qos = {0, 0, 0, 0, 0};

        try {

            mqttClient.subscribe(topics, qos);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void publish(String topic, String payload) {

        try {
//            MqttMessage message = new MqttMessage();
//            message.setPayload(payload.getBytes());
            mqttClient.publish(topic, payload.getBytes(), 0, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
