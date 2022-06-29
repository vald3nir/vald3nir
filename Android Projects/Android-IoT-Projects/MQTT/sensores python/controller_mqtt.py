import threading
import time

import paho.mqtt.client as paho

import sensor

IP_BROKER = "192.168.0.15"


class Sensor(threading.Thread):

    def __init__(self, topic):
        super(Sensor, self).__init__()
        self.topic = topic
        self.client = paho.Client(topic)
        self.client.connect(IP_BROKER)
        self.client.loop_start()

    def run(self):
        while True:
            value = str(sensor.distance()) + " cm"
            print(self.topic, "publishing...", value)
            self.client.publish(self.topic, value)
            time.sleep(3)
        pass


class Atuador(threading.Thread):

    def __init__(self, topic):
        super(Atuador, self).__init__()
        self.topic = topic
        self.client = paho.Client(topic)
        self.client.connect(IP_BROKER)
        self.client.loop_start()
        self.client.on_message = self.on_message
        self.client.subscribe(self.topic, 0)

    def on_message(self, client, userdata, msg):
        status = str(msg.payload)
        print(msg.topic, status)

        if "LER/LED/VERDE" == msg.topic:
            sensor.show_led_verde(status)
        else:
            sensor.show_led_amarelo(status)

    def run(self):
        while True:
            time.sleep(3)
        pass


class SensorDistancia(Sensor):
    def __init__(self):
        super(SensorDistancia, self).__init__("ESCREVER/SENSOR/DISTANCIA")


class AtuadorLEDVerde(Atuador):
    def __init__(self):
        super(AtuadorLEDVerde, self).__init__("LER/LED/VERDE")


class AtuadorLEDAmarelo(Atuador):
    def __init__(self):
        super(AtuadorLEDAmarelo, self).__init__("LER/LED/AMARELO")


if __name__ == '__main__':
    sensor.setup()
    SensorDistancia().start()
    AtuadorLEDVerde().start()
    AtuadorLEDAmarelo().start()
