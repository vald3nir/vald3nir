package vald3nir.demo_mqtt.controller;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import vald3nir.demo_mqtt.R;


public class MQTTController {

    public static final String TOPIC_LED_VERDE = "ESCREVER/LED/VERDE";
    public static final String TOPIC_LED_AMARELO = "ESCREVER/LED/AMARELO";
    public static final String TOPIC_SENSOR_DISTANCIA = "LER/SENSOR/DISTANCIA";

    private MqttAndroidClient mqttAndroidClient;
    private IMQTTController IMQTTController;

    // =============================================================================================

    public MQTTController(IMQTTController IMQTTController) {
        this.IMQTTController = IMQTTController;
    }

    // =============================================================================================

    public boolean isConnected() {
        return mqttAndroidClient != null && mqttAndroidClient.isConnected();
    }

    // =============================================================================================


    public void connect(Context context) {

        String clientId = context.getString(R.string.app_name);
//        String brokerURL = "tcp://172.70.10.138:1883";
        String brokerURL = "tcp://broker.hivemq.com:1883";


        this.mqttAndroidClient = new MqttAndroidClient(context, brokerURL, clientId);
        this.mqttAndroidClient.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                IMQTTController.successConnectionMQTT();
            }

            @Override
            public void connectionLost(Throwable cause) {
                IMQTTController.failConnectionMQTT(cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage messageMQTT) {
                IMQTTController.responseMQTT(topic, messageMQTT);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                try {
                    System.out.println("MQTT: " + token.getMessage().toString());
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);

                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    IMQTTController.failConnectionMQTT(exception);
                }
            });


        } catch (Exception e) {
            IMQTTController.failConnectionMQTT(e);
        }

    }

    // =============================================================================================

    public void publishMessage(String topic, String payload) {

        try {

            MqttMessage message = new MqttMessage();
            message.setPayload(payload.getBytes());
            mqttAndroidClient.publish(topic, message);

        } catch (Exception e) {
            IMQTTController.failSendMessageMQTT(e);
        }
    }

    // =============================================================================================

    public void subscribe(String topic) throws MqttException {

        mqttAndroidClient.subscribe(topic, 0, null, new IMqttActionListener() {

            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            }
        });
    }

    public interface IMQTTController {

        void successConnectionMQTT();

        void failConnectionMQTT(Throwable cause);

        void failSendMessageMQTT(Exception exception);

        void responseMQTT(String topic, MqttMessage messageMQTT);
    }


}
