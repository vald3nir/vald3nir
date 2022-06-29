package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import config.AppsConfig;

public class GatewaySocket extends Thread {

	private HashMap<String, String> addressSubscribers;

	private GatewaySocket() {
		addressSubscribers = new HashMap<>();
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		super.run();

		Socket socket;
		BufferedReader bufferedReader;
		BufferedWriter bufferedWriter;
		ServerSocket serverSocket;

		try {
			serverSocket = new ServerSocket(AppsConfig.PORT_GATEWAY_SOCKET);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		System.out.println("Gateway Online");

		while (true) {

			try {

				socket = serverSocket.accept();

				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				ArrayList<String> lines = readLines(bufferedReader);

				String address = socket.getInetAddress().getHostAddress();
				socket.close();

				for (String line : lines) {

					String[] response = line.split(AppsConfig.DIVIDER);

					System.out.println(address + AppsConfig.DIVIDER + line);

					String action = response[0];
					String topic = response[1];
					String value = response[2];

					if (action.equalsIgnoreCase(AppsConfig.ACTION_PUBLISH)) {

						address = addressSubscribers.get(topic);// .split(AppsConfig.DIVIDER);

						if (address != null) {

							socket = new Socket(address, AppsConfig.PORT_DEFAULT);

							bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
							bufferedWriter.write(topic + AppsConfig.DIVIDER + value + "\n");
							bufferedWriter.flush();

							socket.close();
							break;
						}

					} else if (action.equalsIgnoreCase(AppsConfig.ACTION_SUBSCRIBER)) {
						addressSubscribers.put(topic, address);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private ArrayList<String> readLines(BufferedReader bufferedReader) throws IOException {
		ArrayList<String> lines = new ArrayList<>();
		String line = bufferedReader.readLine();
		while (line != null) {
			lines.add(line);
			line = bufferedReader.readLine();
		}
		return lines;
	}

	public static void main(String[] args) {
		new GatewaySocket().run();
	}

}
