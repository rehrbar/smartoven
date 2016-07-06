#!/usr/bin/env python3

import RPi.GPIO as GPIO
import time
print("RPi.GPIO version: " + GPIO.VERSION)

GPIO.setmode(GPIO.BCM)

GPIO.setup(2,GPIO.OUT)

buttonPins = {12:'UP',13:'RIGHT',14:'DOWN',15:'LEFT'}

def button_callback(channel):
    print("Callback on " + buttonPins[channel])

for pin in buttonPins:
    GPIO.setup(pin,GPIO.IN)
    GPIO.add_event_detect(pin, GPIO.FALLING, callback=button_callback, bouncetime=400)


try:
    while True:
        GPIO.output(2,True)
        time.sleep(2.0)
        GPIO.output(2, False)
        time.sleep(2.0)

except KeyboardInterrupt:
    print("^C received, shutting down client")
    #client.loop_stop()
    GPIO.cleanup()
