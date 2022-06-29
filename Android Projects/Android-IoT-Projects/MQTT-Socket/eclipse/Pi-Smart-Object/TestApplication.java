import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class TestApplication {

    public static void main(String[] args) {

        // sudo javac app.TestApplication.java  -classpath .:classes:/opt/pi4j/lib/'*'
        // sudo java -cp .:/opt/pi4j/lib/'*' app.TestApplication

        try {

            final GpioController gpio = GpioFactory.getInstance();
            final GpioPinDigitalOutput ledPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);

            while (true) {
                ledPin.high();
                System.out.println("Alto");
                Thread.sleep(1000);
                ledPin.low();
                System.out.println("Baixo");
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lascou");
        }
    }

}
