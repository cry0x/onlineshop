# Onlineshop

- [Onlineshop](#onlineshop)
  - [🃏 Services](#-services)
  - [⚙️ Setup](#️-setup)
    - [Production](#production)
    - [Dev-Setup](#dev-setup)
    - [ELK-Stack](#elk-stack)
      - [Kibana](#kibana)
    - [Eureka-Server](#eureka-server)
  - [🗂 Documentation](#-documentation)
    - [API](#api)

## 🃏 Services

-   Product-Service
-   Customer-Service
-   Order-Service
-   Eureka-Service

## ⚙️ Setup

### Production

Zum Starten des gesamten Onlineshops muss lediglich:

```bash
docker-compose --profile prod up -d
```

im Root-Ordner aufgerufen werden. Dann werden alle drei Services mit ihrend Datenbanken gestartet.
Außerdem werden dann noch der Eureka-Service sowie der ELK-Stack gestartet.
Der Eureka-Serivce wird verwendet um die Services registrieren zu können um deren Kommunikation
untereinander erleichtern zu können. Der ELK-Stack wird verwendet um die Logs der Services sammeln
und an einer zentralen Stelle auswerten zu können.

### Dev-Setup

Zur vereinfachung der Entwicklung wurden im docker-compose Profile eingefügt um nur das Starten der Services
zu ermöglich, ohne den ELK-Stack. Dazu muss lediglich der Befehl:

```bash
docker-compose --profile dev up -d
```

ausgeführt werden.

### ELK-Stack

#### Kibana
Es wurde ein Basis-Dashboard erstellt welches importiert werden kann. Dieses lässt sich mit der export-json im
configs-Ordner des Projekts importieren.

### Eureka-Server
Der Eureka-Server bedarf keiner weiteren Einstellung, er dient lediglich der Registrierung der verfügbaren Services.
Nachdem er gestartet wurde melden sich die implementierten Services mit denen eine entsprechende URL zum Eureka-Server
gegeben wurde bei ihm an. Zur Übersicht der verfügbaren Services liefert Eureka eine UI welche über den ``Port: 8761``
erreichbar ist.


## 🗂 Documentation

### API

Die API-Dokumentationen der drei implementierten Services können eingesehen werden, indem ``/swagger-ui.html`` an die
öffentliche Adresse des Services angefügt wird.
`z.B.: localhost:9000/swagger-ui.html`

### Java Documentation
Um die Java-Code-Dokumentation einsehen zu können, muss diese erst mit dem Kommando

```bash
javadoc:javadoc
```

aus dem aktuellen Code erzeugt werden. Danach kann die generierte HTML-Datei über den Browser geöffnet werden.
