# Mocking the API
Da zum Zeitpunkt dieses Hackatons die API noch nicht komplett verfügbar ist
und die Authorisation nicht wie gewünscht funktioniert, wird ein Mock der API
eingesetzt. Diese ist nach den Spezifikationen wie in
https://apiclient.home-connect.com/#/default beschrieben, aufgebaut.

In Zukunft werden noch weitere Funktionen zur API hinzukommen, für unsere
Zwecke reichen jedoch die hiesigen Methoden.

## Setup

Die Installation findet direkt ins `apimock`-Verzeichnis statt. Von dort
aus kann es auch direkt ausgeführt werden.

* Node.js & npm (bereits installiert)
* [apimocker](https://github.com/gstroup/apimocker)

```
cd ./apimock/
npm install apimocker
node node_modules\apimocker\bin\apimocker.js -c config.json
```

Jetzt kann über http://localhost:7878/homeappliances auf die API zugegriffen werden.