import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class Application {

	public static void main(String[] args) throws Exception {
		new Application().run();
	}

	private ApplicationDelegate delegate;
	private GpioPinDigitalOutput ledPin;

	public static final String ADDRESS_BROKEN_MQTT = "tcp://broker.mqttdashboard.com";
	public static final String ADDRESS_GATEWAY_SOCKET = "172.70.10.122";

	public static final int PORT_GATEWAY_SOCKET = 8000;
	public static final int PORT_DEFAULT = 9000;

	// MESSAGE = ACTION - TOPIC - VALUE
	public static final String ACTION_PUBLISH = "action_publish";
	public static final String ACTION_SUBSCRIER = "action_subscriber";

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

	public Application() {
		delegate = new ApplicationDelegate(TOPIC_VALUE_LIGHT,
				TOPIC_CHANGE_LIGHT);
		ledPin = GpioFactory.getInstance().provisionDigitalOutputPin(
				RaspiPin.GPIO_00);
	}

	public void run() throws Exception {
		delegate.startServices(this, "light");
	}

	public void setStatus(String response) {

		if (response.equalsIgnoreCase("on")) {
			ledPin.high();

		} else {
			ledPin.low();
		}
	}

	public class ApplicationDelegate {

		private SocketController socketController;
		private MQTTController mqttController;
		public boolean enable;

		ApplicationDelegate(String topicPublish, String topicSubscriber) {
			socketController = new SocketController(this, topicPublish,
					topicSubscriber);
			mqttController = new MQTTController(this, topicPublish,
					topicSubscriber);
			enable = false;
		}

		void startServices(final Application application, String name)
				throws Exception {
			socketController.connect();
			socketController.setCallback(new ISocketController() {
				public void onResponse(String response) {
					application.setStatus(response);

				}
			});

			mqttController.connect(name);
			mqttController.subscribe(new IMQTTController() {
				public void onResponse(String response) {
					application.setStatus(response);
				}
			});
		}
	}

	public class MQTTController {

		private ApplicationDelegate applicationDelegate;
		private MqttClient sampleClient;
		private String topicPublish, topicSubscriber;

		public MQTTController(ApplicationDelegate applicationDelegate,
				String topicPublish, String topicSubscriber) {
			this.topicPublish = topicPublish;
			this.topicSubscriber = topicSubscriber;
			this.applicationDelegate = applicationDelegate;
		}

		public void connect(String clientId) throws MqttException {

			MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
			mqttConnectOptions.setCleanSession(true);

			sampleClient = new MqttClient(ADDRESS_BROKEN_MQTT, clientId,
					new MemoryPersistence());
			sampleClient.connect(mqttConnectOptions);

			new Thread(publishTask).start();
		}

		public void subscribe(final IMQTTController callback)
				throws MqttException {
			if (sampleClient != null) {
				sampleClient.subscribe(topicSubscriber,
						new IMqttMessageListener() {
							public void messageArrived(String topic,
									MqttMessage message) throws Exception {
								if (topicSubscriber.equalsIgnoreCase(topic)) {
									String response = new String(message
											.getPayload());
									applicationDelegate.enable = response
											.equalsIgnoreCase("on");
									callback.onResponse(response);
								}
							}
						});
			}
		}

 private String createMessageMQTT() {
        return "MQTT" + DIVIDER + (int) (Math.random() * 3 + 47);
    }

		private Runnable publishTask = new Runnable() {

			public void run() {

				MqttMessage mqttMessage = new MqttMessage(createMessageMQTT().getBytes());
				mqttMessage.setQos(0);

				while (true) {

					try {

						if (applicationDelegate.enable) {
							sampleClient.publish(topicPublish, mqttMessage);
						}

						Thread.sleep(1000);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

		};

	}

	public class SocketController {

		private ISocketController callback;
		private String topicPublish, topicSubscriber;
		private ApplicationDelegate applicationDelegate;

		public SocketController(ApplicationDelegate applicationDelegate,
				String topicPublish, String topicSubscriber) {
			this.topicSubscriber = topicSubscriber;
			this.topicPublish = topicPublish;
			this.applicationDelegate = applicationDelegate;
		}

		public void setCallback(ISocketController callback) {
			this.callback = callback;
		}

  private String createMessageSocket() {
        return "socket" + DIVIDER + (int) (Math.random() * 3 + 47);
    }

		private Runnable subscriberTask = new Runnable() {

			public void run() {

				try {

					// SUBSCRIBER
					// =======================================================================================
					Socket socket = new Socket(ADDRESS_GATEWAY_SOCKET,
							PORT_GATEWAY_SOCKET);

					BufferedWriter bufferedWriter = new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream()));
					bufferedWriter.write(ACTION_SUBSCRIER + DIVIDER
							+ topicSubscriber + DIVIDER + createMessageSocket() + "\n");
					bufferedWriter.flush();

					socket.close();
					// =======================================================================================

					ServerSocket serverSocket = new ServerSocket(PORT_DEFAULT);

					while (true) {

						socket = serverSocket.accept();

						BufferedReader bufferedReader = new BufferedReader(
								new InputStreamReader(socket.getInputStream()));
						String response = bufferedReader.readLine().split(
								DIVIDER)[1];
						socket.close();

						applicationDelegate.enable = response
								.equalsIgnoreCase("on");

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
			public void run() {

				Socket socket;
				BufferedWriter bufferedWriter;

				while (true) {

					try {

						if (applicationDelegate.enable) {

							socket = new Socket(ADDRESS_GATEWAY_SOCKET,
									PORT_GATEWAY_SOCKET);

							bufferedWriter = new BufferedWriter(
									new OutputStreamWriter(
											socket.getOutputStream()));
							bufferedWriter.write(ACTION_PUBLISH + DIVIDER
									+ topicPublish + DIVIDER + "OK\n");
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

	}

	public interface ISocketController {
		void onResponse(String response);
	}

	public interface IMQTTController {
		void onResponse(String response);
	}

}
