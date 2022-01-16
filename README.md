# onlineshop

## Services
- Product-Service
- Customer-Service
- Order-Service
- Eureka-Service

## Setup
### Production
Zum Starten des gesamten Onlineshops muss lediglich: ```docker-compose --profile prod up -d``` im Root-Ordner
aufgerufen werden. Dann werden alle drei Services mit ihrend Datenbanken gestartet. 
Außerdem werden dann noch der Eureka-Service sowie der ELK-Stack gestartet.
Der Eureka-Serivce wird verwendet um die Services registrieren zu können um deren Kommunikation 
untereinander erledichtern zu können. Der ELK-Stack wird verwendet um die Logs der Services sammeln
und an einer zentralen Stelle auswerten zu können.
### Dev-Setup
Zur vereinfachung der Entwicklung wurden im docker-compose Profile eingefügt um nur das Starten der Services
zu ermöglich, ohne den ELK-Stack. Dazu muss lediglich der Befehl: ```docker-compose --profile dev up -d``` 
ausgeführt werden.
### ELK-Stack
#### Kibana
Es wurde ein Basis-Dashboard erstellt welches importiert werden kann. Dieses lässt sich mit der export-json im 
configs-Ordner des Projekts importieren.  