# smartoven
Anwendung zur Steuerung von HomeConnect-Geräten für Leute mit
eingeschränktem Sehvermögen

## Hardware

* Raspberry Pi 2 mit Micro SD card
* 4x Buttons (wir verwenden ein Linkerkit Hat mit 4 Button Modules)
* Optional: LED zur Statusanzeige mit passendem Widerstand.

## Software

* Raspbian Jessie Lite
* Python3
* OpenJDK 8 JDK
* Maven3
* espeak
* mosquitto (mqtt Broker)
* paho-mqtt (mqtt Client)

## Installation

Installation von mosquitto:
```
sudo apt-get install mosquitto
```

Installation von espeak für die Sprachausgabe:
```
sudo apt-get install espeak
```

Installation der Build-Tools für Java:
```
sudo apt-get install openjdk-8-jdk maven
```

Installation von Python3 und dazugehörigen Paketen:
```
sudo apt-get install python3 python3-pip python3-rpi.gpio
sudo pip3 install paho-mqtt
```

### Projekt klonen
```
git clone https://github.com/rehrbar/smartoven.git
```

### Java Client erzeugen
```
mvn clean package
```

## Start
```
python  ./buttonListener.py
java -jar ./smartoven/target/smartoven-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```