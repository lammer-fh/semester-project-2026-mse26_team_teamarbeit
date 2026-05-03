## 1. API Specification
### KI Specs
- Github Copilot 
- Gemini 3.1 Pro
### Beschreibung / Prompt
Nachdem ich die API Specification nach bestem Wissen und Gewissen ohne KI Unterstützung definiert hatte, hab ich sowohl die Angaben (project-assignment2026.pdf, ProjektSpezifikation2026.pdf) als auch mein API_specification.md File analysieren lassen und mit folgendem Prompt nach Verbesserungen gesucht:

```
You are an expert software engineer and software solution architect.

Analyze both the "project-assignment2026.pdf" and "ProjektSpezifikation2026.pdf" files and then look at the current API_specification.md.
How would you design the API_Specification?
What is currently wrong or missing in my file?
```

### Ergebnis
Was konkret falsch oder fehlend an der API_specification.md ist:
Hier ist die finale und zusammenfassende Kritik an der aktuellen API_specification.md, basierend auf beiden Dokumenten:

1. Fehlende Paginierung (U2)
Constraint: "A maximum of 5 hotel rooms should be displayed on the first load... it is possible to navigate to further pages (pagination)."
Fehler in der API: Der Endpunkt GET /rooms liefert starr die Form { "rooms": [...] }.
Korrektur: Der Endpunkt erfordert Parameter für die Paginierung (z.B. ?page=0&size=5) und muss Paginierungs-Metadaten zurückgeben:

2. DTO "Room" ist unvollständig (U2 & U5)
Constraint: "[...] their details ... The extras are accompanied by icons" (U2) und "The hotel room including image, title, extras, and description" (U5).
Fehler in der API: Im Room-Modell fehlt das Feld für extras (z.B. ["WIFI", "BALCONY"]) komplett.
Korrektur: Das Room-DTO muss um extras (als String-Array) erweitert werden.

3. Falscher Architektur-Ansatz für Bilder (Anti-Pattern)
Constraint: "Room images are located in the images/rooms folder of the repository." (U2)
Fehler in der API: Du stellst Bilder als Base64-Strings per API über GET /rooms/:id/images bereit. Das ist extrem ineffizient, sprengt Paginierungs-Payloads und erfüllt nicht den Standard von Web Application Frameworks.
Korrektur: Im Entity/DTO Room sollte lediglich eine (oder mehrere) URL(s) übertragen werden, z.B. "imageUrl": "/images/rooms/room_42.jpg". Die Base64/Image-API-Endpunkte sollten komplett aus der Spezifikation entfernt werden, da statische Assets direkt via HTTP-Server ausgeliefert werden.

4. Kein Verfügbarkeits-Endpunkt (U3)
Constraint: "U3: Check Availability... An API endpoint is available to retrieve room availability."
Fehler in der API: Es gibt keinen Endpunkt, der die Verfügbarkeit eines Zimmers für einen gewissen Zeitraum prüft.
Korrektur: Du benötigst zwingend einen neuen Endpunkt (z.B. GET /rooms/:id/availability?from=YYYY-MM-DD&to=YYYY-MM-DD), der prüft, ob Buchungskonflikte existieren und ein klares true/false an das Vue.js/Pinia-Frontend liefert.

5. Erstellen von Buchungen unlogisch / fehleranfällig (U4 & U5)
Constraint: "The following data must be provided: First name, Last name, Valid email address..." (U4).
Fehler in der API: Der Endpoint POST /bookings erzwingt die Übermittlung einer userId. Das würde einen vorangegangenen POST-Call an users (und damit verbundene transaktionale Probleme) erfordern. Das ist weder nutzerfreundlich noch "Clean".
Korrektur: Der Endpunkt POST /bookings sollte ein komplexeres DTO erwarten, das die Gastdaten direkt enthält:

6. Fehlende Dokumentation von Fehlermeldungen (Validierung)
Constraint: "Proper error handling, validations" (Final Delivery).
Fehler in der API: Deine Fehler sind sehr generisch ({ "errorMessage": "string" }).
Korrektur: Ein standardisiertes Fehlerobjekt bei Formularfehlern (wie z.B. in Spring Boot üblich: eine Liste von Feld-Fehlern) wäre sinnvoll, damit das Vue3 Frontend dem User sagen kann, welches Feld falsch ausgefüllt ist.
### Next Steps
Ich habe dementsprechend die Punkte (1), (2), (3), (4) und (5) übernommen.

## 2. DB_Design
### Beschreibung
Selbiges habe ich nun auch für das DB Schema gemacht. Im selben KI-Chat habe ich folgenden Prompt genutzt: `now please do the same for my db_design.md`
### Ergebnis
1. Die M:N-Beziehung zwischen ROOM und ROOM_EXTRA
Aktuell: ROOM }o--o{ ROOM_EXTRA
Analyse: Im rein logischen ER-Diagramm ist eine Many-to-Many-Beziehung (M:N) erlaubt. In einer relationalen MySQL-Datenbank kann man das aber nicht direkt abbilden. Du benötigst dafür eine Junction Table (Verbindungstabelle).
Lösungsvorschlag für das Diagramm & Spring Boot:
Beim Implementieren mit JPA nutzt du @ManyToMany mit einer @JoinTable. Um das ER-Diagramm näher an das tatsächliche MySQL-Schema zu bringen, empfiehlt es sich oft, die Verbindungstabelle explizit im Mermaid einzuzeichnen, z.B. ROOM_HAS_EXTRA mit RoomId und ExtraName als zusammengesetztem Primärschlüssel. Alternativ kannst du es als abstraktes Modell so belassen, solltest es im Hinterkopf aber für Milestone 2 wissen.

2. Datentyp DateTime für Buchungen
Aktuell: From DateTime und To DateTime in BOOKING
Analyse: Ein Hotelzimmer wird tageweise gebucht, Check-in und Check-out Zeiten sind in der Regel fix (z.B. ab 15:00 Uhr, bis 11:00 Uhr). Wenn du einen DateTime (oder TIMESTAMP) verwendest, riskierst du Zeitzonen-Bugs im Frontend (ein Raum wird am 01.05. gebucht, aber im UI als 30.04. 23:00 Uhr angezeigt). Die API-Spec verwendet für das JSON bereits YYYY-MM-DD.
Lösungsvorschlag: Ändere den Typen auf Date (in MySQL entspricht das DATE ohne Zeitstempel).

3. "Calculated" Felder: Duration und TotalPrice
Aktuell: Beide sind als "Calculated" markiert.
Analyse:

TotalPrice in der DB zu speichern, ist absolut richtig (Best Practice)! Wenn das Hotel nächstes Jahr den PricePerNight im ROOM anhebt, dürfen alte Rechnungen/Buchungen sich nicht automatisch verändern. Der Preis zum Zeitpunkt der Buchung muss als "Snapshot" eingefroren werden.
Duration: Die Dauer der Nächte lässt sich jederzeit mit 100%iger Genauigkeit berechnen (DATEDIFF(To, From)). Das Speichern als eigene Spalte redundant und eine Verletzung der Normalisierungsregeln (3. Normalform). Das kann im Backend On-the-Fly generiert werden.
Lösungsvorschlag: Entferne Duration als Feld aus der Datenbank-Tabelle. Es reicht, wenn das Backendmaß es für das DTO (API-Ausgabe) berechnet.
4. Umgang mit Stornierungen (U4: "Cancel Booking")
Aktuell: Die API hat einen Endpoint für DELETE /bookings/:id. Im ER-Diagramm gibt es keine Hinweise auf den Buchungsstatus.
Analyse: Buchungsdaten haben oft eine rechtliche und buchhalterische Relevanz. Ein hartes Löschen (Hard Delete, via DELETE FROM booking...) ist meistens unerwünscht.
Lösungsvorschlag: Füge der Tabelle BOOKING einen Status hinzu, z.B. Status string (Values: z.B. "CONFIRMED", "CANCELLED"). So kannst du beim "Stornieren" einfach den Status updaten, anstatt den Datensatz endgültig aus MySQL zu löschen (sog. Soft-Delete).
### Next Steps
Ich habe dementsprechend die Punkte (2), (3) und (4) übernommen und das Schema angepasst. Punkt (1) habe ich absichtlich nicht übernommen, da durch die zusätzliche Tabelle meiner Meinung nach kein Mehrwert geschaffen wird.