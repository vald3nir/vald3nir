import time

import RPi.GPIO as GPIO

trigger = 21
echo = 16
led_amarelo = 26
led_verde = 12


def setup():
    GPIO.setmode(GPIO.BCM)

    GPIO.setup(trigger, GPIO.OUT)
    GPIO.setup(echo, GPIO.IN)

    GPIO.setup(led_amarelo, GPIO.OUT)
    GPIO.setup(led_verde, GPIO.OUT)


def show_led_verde(status):
    if '1' == status:
        GPIO.output(led_verde, GPIO.HIGH)
    else:
        GPIO.output(led_verde, GPIO.LOW)


def show_led_amarelo(status):
    if '1' == status:
        GPIO.output(led_amarelo, GPIO.HIGH)
    else:
        GPIO.output(led_amarelo, GPIO.LOW)


def distance():
    GPIO.output(trigger, True)
    time.sleep(0.00001)
    GPIO.output(trigger, False)

    StartTime = time.time()
    StopTime = time.time()

    while GPIO.input(echo) == 0:
        StartTime = time.time()

    while GPIO.input(echo) == 1:
        StopTime = time.time()

    TimeElapsed = StopTime - StartTime
    distance = (TimeElapsed * 34300) / 2
    return distance
