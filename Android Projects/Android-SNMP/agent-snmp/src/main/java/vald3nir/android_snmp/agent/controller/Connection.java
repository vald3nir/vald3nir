package vald3nir.android_snmp.agent.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import vald3nir.android_snmp.agent.view.AgentDelegate;

public class Connection extends Thread {

    private Socket socket;
    private BufferedReader bufferedReader;
    private AgentDelegate delegate;

    //==========================================================================================

    public Connection(AgentDelegate delegate) throws IOException {

        this.delegate = delegate;
        this.socket = delegate.getSocket();

        try {

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==========================================================================================

    public void run() {

        try {

            delegate.writeLog("Conexão com: " + socket.getInetAddress().getHostAddress());

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

            String managerRequest = bufferedReader.readLine();
            delegate.writeLog("Gerente -> " + managerRequest);

            String getResponse = delegate.generateGetResponse(managerRequest, socket);

            bufferedWriter.write(getResponse + "\r\n");
            bufferedWriter.flush();

            delegate.writeLog("Agente -> " + getResponse);
            delegate.writeLog("Gerente desconectou");

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
