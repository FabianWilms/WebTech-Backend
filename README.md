# WebTech-Backend

## Lokal starten

https://github.com/FabianHoltkoetter/WebTech-Backend/wiki/Wie-kann-ich-das-Projekt-lokal-zum-Laufen-bringen%3F

## API-Dokumentation

### Endpunkte

* `/topics` - Endpunkt für Themengebiete
* `/exercises` - Endpunkt für _alle_ Aufgabentypen kombiniert
* `/clozes` - Endpunkt für Lückentext-Aufgaben
* `/multiplechoices` - Enpunkt für Multiple Choice-Aufgaben
* `/singlechoices` - Endpunkt für Single Choice-Aufgaben
* `/associations` - Endpunkt für Zuordnungs-Aufgaben

Jeder Endpunkt unterstützt die HTTP-Anfragen `GET`(lesen), `POST`(erstellen), `PUT`(aktualisieren) und `DELETE`(löschen).
Um auf eine bestimmte Entität zuzugreifen muss hinter dem Endpunkt `/{ID}` angegeben werden.

### Datenformat

Die Daten der Endpunkte sind im JSON-Format. Eine `GET`-Anfrage an `/topics/1` erzeugt zum beispiel folgende Antwort:

```JSON
{
  "name": "Java",
  "_links": {
    "self": {
      "href": "https://webtech-its.herokuapp.com/topics/1"
    },
    "topic": {
      "href": "https://webtech-its.herokuapp.com/topics/1"
    }
  }
}
```

Zunächst stehen im JSON alle Daten der Entität. Im Falle des Topics der Name. Anschließen folgt eine Liste von HATEOS-Links.
Der self-Link verweist dabei immer auf diese Entität und dient auch als ID für etwaige Such-Anfragen.

## Domain-Modell

![domain](https://cloud.githubusercontent.com/assets/18287674/15497563/98635ec4-219b-11e6-8051-19f52faffaf3.PNG)
