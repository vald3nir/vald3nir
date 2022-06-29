package vald3nir.demo_mqtt.view;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import vald3nir.demo_mqtt.controller.MQTTController;

import static vald3nir.demo_mqtt.controller.MQTTController.TOPIC_LED_AMARELO;
import static vald3nir.demo_mqtt.controller.MQTTController.TOPIC_LED_VERDE;
import static vald3nir.demo_mqtt.controller.MQTTController.TOPIC_SENSOR_DISTANCIA;

public class DashboardDelegate implements MQTTController.IMQTTController {

    private DashboardActivity dashboardActivity;
    private MQTTController controller;


    public DashboardDelegate(DashboardActivity dashboardActivity) {
        this.dashboardActivity = dashboardActivity;
        controller = new MQTTController(this);
        controller.connect(dashboardActivity);
    }

    public void mudarValorLedAmarelo(boolean acaoLigar) {
        String msg = acaoLigar ? "1" : "0";
        controller.publishMessage(TOPIC_LED_AMARELO, msg);
        dashboardActivity.escreverLog(TOPIC_LED_AMARELO + " = " + msg);
    }

    public void mudarValorLedVerde(boolean acaoLigar) {
        String msg = acaoLigar ? "1" : "0";
        controller.publishMessage(TOPIC_LED_VERDE, msg);
        dashboardActivity.escreverLog(TOPIC_LED_VERDE + " = " + msg);

    }

    @Override
    public void successConnectionMQTT() {
        dashboardActivity.escreverLog("Conexão MQTT OK!");
        try {
            controller.subscribe(TOPIC_SENSOR_DISTANCIA);
        } catch (MqttException e) {
            dashboardActivity.escreverLog(e.getMessage());
        }
    }

    @Override
    public void failConnectionMQTT(Throwable cause) {
        dashboardActivity.escreverLog(cause.getMessage());
    }

    @Override
    public void failSendMessageMQTT(Exception exception) {
        dashboardActivity.escreverLog(exception.getMessage());
    }

    @Override
    public void responseMQTT(String topic, MqttMessage messageMQTT) {
        if (TOPIC_SENSOR_DISTANCIA.equalsIgnoreCase(topic)) {
            String msg = new String(messageMQTT.getPayload());
            dashboardActivity.atualizarDistancia(msg);
            dashboardActivity.escreverLog(TOPIC_SENSOR_DISTANCIA + " = " + msg);
        }
    }
}
