#!/usr/bin/env python3

import RPi.GPIO as GPIO
import time
import paho.mqtt.client as mqtt
print("RPi.GPIO version: " + GPIO.VERSION)

buttonPins = {5:'UP',12:'RIGHT',14:'DOWN',19:'LEFT'}
ledPin = 2
client = mqtt.Client()

# callbacks
def button_callback(channel):
    print("Callback on " + buttonPins[channel])
    client.publish("/button", buttonPins[channel])

def on_connect(client, userdata, flags, rc):
    print("Connected to broker with result code " + str(rc))

GPIO.setmode(GPIO.BCM) # BCM numbering should be the one as labelled on the shield
GPIO.setup(ledPin,GPIO.OUT) # LED output

# Enable all buttons and add event
for pin in buttonPins:
    GPIO.setup(pin,GPIO.IN)
    GPIO.add_event_detect(pin, GPIO.FALLING, callback=button_callback, bouncetime=600)

client.on_connect = on_connect
client.connect("localhost", 1883)
client.loop_start()

try:
    # Blink led as status light
    while True:
        GPIO.output(ledPin, True)
        time.sleep(2.0)
        GPIO.output(ledPin, False)
        time.sleep(2.0)

except KeyboardInterrupt:
    print("^C received, shutting down client")
    client.loop_stop()
    GPIO.cleanup()
