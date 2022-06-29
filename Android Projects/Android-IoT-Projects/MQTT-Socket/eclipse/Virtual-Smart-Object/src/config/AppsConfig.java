package config;

public class AppsConfig {

    public static final String ADDRESS_BROKEN_MQTT = "tcp://broker.mqttdashboard.com";
    public static final String ADDRESS_GATEWAY_SOCKET = "172.70.10.122";
//    public static final String ADDRESS_GATEWAY_SOCKET = "192.168.0.102";

    public static final int PORT_GATEWAY_SOCKET = 8000;
    public static final int PORT_DEFAULT = 9000;

    // MESSAGE = ACTION - TOPIC - VALUE
    public static final String ACTION_PUBLISH = "pub";
    public static final String ACTION_SUBSCRIBER = "sub";

    public static final String DIVIDER = "-";

    // CLIENT PUBLISH && SENSOR SUBSCRIBER
    public static final String TOPIC_CHANGE_LIGHT = "change/light";
    public static final String TOPIC_CHANGE_AIR = "change/air";
    public static final String TOPIC_CHANGE_TV = "change/tv";
    public static final String TOPIC_CHANGE_ALARM = "change/alarm";
    public static final String TOPIC_CHANGE_PC = "change/pc";

    // CLIENT SUBSCRIBER && SENSOR PUBLISH
    public static final String TOPIC_VALUE_LIGHT = "value/light";
    public static final String TOPIC_VALUE_AIR = "value/air";
    public static final String TOPIC_VALUE_TV = "value/tv";
    public static final String TOPIC_VALUE_ALARM = "value/alarm";
    public static final String TOPIC_VALUE_PC = "value/pc";

    public static String createMessageToPublish(String topicPublish, boolean isSocketMessage) {
        if (isSocketMessage) {
            return ACTION_PUBLISH + DIVIDER + topicPublish + DIVIDER + "OK\n";
        } else {
            return ACTION_PUBLISH + DIVIDER + topicPublish + DIVIDER + "OK";
        }
    }

    public static String createMessageToSubscriber(String topicPublish, boolean isSocketMessage) {
        if (isSocketMessage) {
            return ACTION_SUBSCRIBER + DIVIDER + topicPublish + DIVIDER + "OK\n";
        } else {
            return ACTION_SUBSCRIBER + DIVIDER + topicPublish + DIVIDER + "OK";
        }
    }
}