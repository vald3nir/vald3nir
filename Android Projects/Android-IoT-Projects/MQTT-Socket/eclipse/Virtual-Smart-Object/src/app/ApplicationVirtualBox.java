package app;

import java.awt.EventQueue;

import config.AppsConfig;
import smart_object.SmartObject;

public class ApplicationVirtualBox {

	public static class Air extends SmartObject {
		private Air() throws Exception {
			super("Air", AppsConfig.TOPIC_VALUE_AIR, AppsConfig.TOPIC_CHANGE_AIR);
		}
	}

	public static class Alarm extends SmartObject {
		private Alarm() throws Exception {
			super("Alarm", AppsConfig.TOPIC_VALUE_ALARM, AppsConfig.TOPIC_CHANGE_ALARM);
		}
	}

	public static class TV extends SmartObject {
		private TV() throws Exception {
			super("TV", AppsConfig.TOPIC_VALUE_TV, AppsConfig.TOPIC_CHANGE_TV);
		}
	}

	public static class PC extends SmartObject {
		private PC() throws Exception {
			super("PC", AppsConfig.TOPIC_VALUE_PC, AppsConfig.TOPIC_CHANGE_PC);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
//				new Air().run();
//				new Alarm().run();
				new TV().run();
//				new PC().run();

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}