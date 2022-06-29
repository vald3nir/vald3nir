package smart_object;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class SmartObject {

	private JLabel jLabelStatus;
	private SmartObjectDelegate delegate;
	private String name;

	public SmartObject(String name, String topicPublish, String topicSubscriber) throws Exception {
		this(name, topicPublish, topicSubscriber, true);
	}

	public SmartObject(String name, String topicPublish, String topicSubscriber, boolean show) throws Exception {

		delegate = new SmartObjectDelegate(topicPublish, topicSubscriber);

		this.name = name;
		if (show) {
			showWindow();
		}
	}

	private void showWindow() {

		JFrame jFrame = new JFrame();

		jFrame.setTitle(name);
		jFrame.setResizable(false);
		jFrame.getContentPane().setBackground(Color.WHITE);
		jFrame.setBounds(100, 100, 450, 300);
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.getContentPane().setLayout(new GridLayout(3, 2, 0, 0));

		jFrame.getContentPane().add(createJLabel("Object"));
		jFrame.getContentPane().add(createJLabel(name));

		jFrame.getContentPane().add(createJLabel("IP"));
		jFrame.getContentPane().add(createJLabel(delegate.getIP()));

		jFrame.getContentPane().add(createJLabel("Status"));

		jLabelStatus = createJLabel("Off");
		jFrame.getContentPane().add(jLabelStatus);

		jFrame.setVisible(true);
	}

	private JLabel createJLabel(String title) {
		JLabel jLabel = new JLabel(title, SwingConstants.CENTER);
		jLabel.setFont(new Font("Serif", Font.PLAIN, 24));
		return jLabel;
	}

	public void run() throws Exception {
		delegate.startServices(this, name);
	}

	public void setStatus(String status) {
		jLabelStatus.setText(status);
	}

}
