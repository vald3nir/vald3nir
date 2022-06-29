package vald3nir.manager_snmp.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import static vald3nir.manager_snmp.core.MIBs.PORT_SNMP;

public class Manager extends JFrame {

    // =============================================================================================

    private final JTextArea log = new JTextArea(10, 20);
    private final JTextField community = new JTextField();

    private final OidHandler oidHandler = new OidHandler();
    private final ManagerHandler managerHandler = new ManagerHandler(log, community);

    // =============================================================================================

    private void listenTraps() {

        Runnable r = () -> {

            ServerSocket serverSocket;

            try {

                serverSocket = new ServerSocket(PORT_SNMP);

                while (true) {
                    Socket socket = serverSocket.accept();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    log.append("\nTrap:\n" + bufferedReader.readLine() + "\n");
                    socket.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        new Thread(r).start();
    }

    // =============================================================================================

    private void createView() {

        JPanel context = new JPanel();
        context.setBackground(Color.WHITE);
        context.setBounds(0, 0, 445, 438);
        context.setLayout(null);
        getContentPane().add(context);

        // =========================================================================================

        JLabel labelMibs = new JLabel("Mibs:");
        labelMibs.setBounds(12, 12, 38, 15);
        labelMibs.setHorizontalAlignment(SwingConstants.CENTER);
        context.add(labelMibs);

        // =========================================================================================

        JPanel panelListMibs = new JPanel();
        panelListMibs.setBackground(Color.WHITE);
        panelListMibs.setBounds(12, 39, 421, 166);
        context.add(panelListMibs);
        this.oidHandler.createPanelOids(panelListMibs);

        // =========================================================================================

        JLabel labelCommunity = new JLabel("Comunidade:");
        labelCommunity.setHorizontalAlignment(SwingConstants.CENTER);
        labelCommunity.setBounds(202, 12, 93, 15);
        context.add(labelCommunity);

        // =========================================================================================

        community.setHorizontalAlignment(SwingConstants.CENTER);
        community.setText("ANDROID");
        community.setBounds(319, 10, 114, 19);
        community.setColumns(10);
        context.add(community);

        // =========================================================================================

        JButton btnGetRequest = new JButton("Get [0]");
        btnGetRequest.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (oidHandler.getOidsSelected().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Escolha uma mib!");

                } else {
                    managerHandler.getRequest(oidHandler.getOidsSelected());
                }
            }
        });
        btnGetRequest.setBounds(12, 235, 117, 25);
        context.add(btnGetRequest);

        // =========================================================================================

        JButton btnGetNext = new JButton("Get Next [1]");
        btnGetNext.setBounds(165, 235, 117, 25);
        context.add(btnGetNext);
        btnGetNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                managerHandler.getNextRequest();
            }
        });

        // =========================================================================================

        final JCheckBox enableAlarm = new JCheckBox("Ativar alarme");
        enableAlarm.setBackground(Color.WHITE);
        enableAlarm.setBounds(314, 211, 119, 23);
        context.add(enableAlarm);

        // =========================================================================================

        JButton btnSet = new JButton("Set [3]");
        btnSet.setBounds(316, 235, 117, 25);
        context.add(btnSet);
        btnSet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                managerHandler.setRequest(oidHandler.getOIDEnableOrDesableTrap(), enableAlarm.isSelected());
            }
        });

        // =========================================================================================

        log.setLineWrap(true);
        log.setBackground(new Color(240, 240, 240));

        JScrollPane scroll = new JScrollPane(log);
        scroll.setBounds(12, 272, 421, 115);
        context.add(scroll);

        // =========================================================================================

        JButton clearLog = new JButton("Limpar Log");
        clearLog.setBounds(12, 399, 421, 25);
        context.add(clearLog);
        clearLog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                log.setText("");
            }
        });
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {

                Manager frame = new Manager();

                frame.setTitle("Gerente SNMP");
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setSize(448, 465);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.getContentPane().setLayout(null);

                frame.createView();

                frame.listenTraps();


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}