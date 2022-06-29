package vald3nir.android_snmp.agent.view;

import android.annotation.SuppressLint;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import vald3nir.android_snmp.agent.controller.SystemCallsManager;
import vald3nir.android_snmp.agent.controller.Connection;
import vald3nir.android_snmp.agent.controller.MessageController;

import static vald3nir.manager_snmp.core.MIBs.PORT_SNMP;

public class AgentDelegate {

    private MessageController messageController;
    private ServerSocket serverSocket;
    public String ipManager = null;
    public String messageTrap;
    public boolean enableTrap = false;
    private AgentActivity agentActivity;

    AgentDelegate(AgentActivity agentActivity) {
        this.agentActivity = agentActivity;
        this.messageController = new MessageController(this, agentActivity);
    }

    @SuppressLint("SetTextI18n")
    void fillAddressView(TextView ipTextView, TextView port) {
        ipTextView.setText(SystemCallsManager.getCurrentIP());
        port.setText(PORT_SNMP.toString());
    }


    public Socket getSocket() throws IOException {
        return serverSocket.accept();
    }

    void listenManagers() {

        new Thread(() -> {

            try {

                serverSocket = new ServerSocket(PORT_SNMP);

                while (true) {
                    new Connection(this).start();
                    Thread.sleep(1000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    void startServiceTrap() {

        new Thread(() -> {

            while (true) try {

                if (enableTrap && ipManager != null) {

                    InetAddress inteAddress = InetAddress.getByName(ipManager);
                    SocketAddress socketAddress = new InetSocketAddress(inteAddress, PORT_SNMP);

                    Socket socket = new Socket();

                    int timeoutInMs = 10 * 1000;   // 10 segundos
                    socket.connect(socketAddress, timeoutInMs);

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bufferedWriter.write(messageTrap);
                    bufferedWriter.flush();

                    socket.close();
                }

                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void writeLog(String log) {
        this.agentActivity.writeLog(log);
    }

    public String generateGetResponse(String managerRequest, Socket socket) {
        return messageController.generateGetResponse(managerRequest, socket);
    }
}
