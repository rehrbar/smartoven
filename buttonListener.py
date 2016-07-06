#!/usr/bin/env python3

import RPi.GPIO as GPIO
import time
print("RPi.GPIO version: " + GPIO.VERSION)

GPIO.setmode(GPIO.BCM)

GPIO.setup(2,GPIO.OUT)

buttonPins =  [12,13,14,15]
GPIO.setup(buttonPins,GPIO.IN, pull_up_down=GPIO.PUD_UP)

def button_callback(channel):
    print("Callback on " + channel)


GPIO.add_event_detect(buttonPins, GPIO.FALLING, callback=button_callback, bouncetime=200)

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
