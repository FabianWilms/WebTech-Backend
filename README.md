# WebTech-Backend

## Lokal starten

1. Clone
2. Import into IDE
3. Maven install
4. Run ItsApplication.java

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

![domain](https://docs.google.com/drawings/d/1lu95IIlvC_vYBZsw5VztX_c7M5aR_ThyMZtHFN8EAsI/pub?w=828&amp;h=589)
