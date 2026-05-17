# AI Usage:

## 1. Backend Services und Controller für die Spring Boot API

### KI Tool

- ChatGPT 5.5 / Codex

### Ziel

Nachdem die grundlegenden Spring Boot Entities und Repositories bereits im Backend vorhanden waren, sollte mithilfe von KI der aktuelle Stand analysiert und die noch fehlenden Services und Controller für die API umgesetzt werden.

Der Fokus lag dabei auf dem Ordner `Backend` und der vorhandenen API-Spezifikation in:

```text
Documentation/API_specification.md
```

Ziel war es, die Backend-Struktur so zu erweitern, dass die wichtigsten REST-Endpunkte für Zimmer, Buchungen und Benutzer vorhanden sind und sich an der API-Spezifikation orientieren.

### Beschreibung / Prompt

Ich habe ChatGPT / Codex folgenden Auftrag gegeben:

```text
i wanna complete our group projects backend today, my buddy has started implementing spring boot entities, repositories and i want to complete the missing services and controllers. take a first look for me to check the overall progress. i have a strong fullstack background but never really worked with spring boot outside of uni

thats our api specs : Documentation/API_specification.md

focus on the backend folder where i want to expand on today. I want to complete all necessary Services and Controllers for today
```

Danach habe ich mir die Änderungen erklaeren lassen, um besser zu verstehen, wie die Umsetzung in Spring Boot aufgebaut ist:

```text
explain all changes you made to me as i am still studying spring boot but have learned it in theory and have decent fullstack experience
```

### Analyse durch die KI

Codex hat zuerst die vorhandene Backend-Struktur analysiert. Dabei wurden insbesondere folgende Punkte festgestellt:

1. Es gab bereits Entities fuer `Room`, `RoomType`, `RoomImage`, `RoomExtra`, `Guest` und `Booking`.

2. Es gab bereits Repositories fuer diese Entities, aber noch kaum Query-Methoden fuer die benötigten API-Funktionen.

3. Es existierte nur ein erster `RoomController` mit `GET /rooms`.

4. Die vorhandene DTO-Klasse `RoomReadDto` entsprach nicht der API-Spezifikation, da sie andere Feldnamen verwendete.

5. In `BookingService.java` war ein schwerer Fehler vorhanden: Die Datei enthielt erneut eine Klasse mit dem Namen `RoomService`. Dadurch haette das Backend nicht korrekt kompiliert.

6. Die API-Spezifikation verwendet den Begriff `User`, waehrend das Backend-Modell aktuell die Entity `Guest` besitzt. Die KI hat daher vorgeschlagen, die API-User vorerst auf die bestehende `Guest`-Entity zu mappen.

### Ergebnis / Umgesetzte Änderungen

#### Controller

Es wurden Controller für die drei zentralen API-Bereiche erstellt bzw. erweitert:

- `RoomController`
- `BookingController`
- `UserController`

Folgende Endpunkte wurden umgesetzt:

```text
GET    /rooms
GET    /rooms/{id}
GET    /rooms/{id}/availability
GET    /rooms/{id}/bookings

GET    /bookings
POST   /bookings
PUT    /bookings/{id}
DELETE /bookings/{id}

GET    /users
GET    /users/{id}
POST   /users
PUT    /users/{id}
DELETE /users/{id}
GET    /users/{id}/bookings
```

Die Controller wurden bewusst schlank gehalten. Sie nehmen Request-Parameter, Pfadvariablen oder Request-Bodies entgegen und leiten die eigentliche Logik an die Service-Klassen weiter.

#### Services

Es wurden drei Service-Klassen erstellt bzw. überarbeitet:

- `RoomService`
- `BookingService`
- `UserService`

Der `RoomService` übernimmt unter anderem:

- Laden von Zimmern mit Pagination
- Filtern von Zimmern nach Name
- Sortieren nach Feldern wie `pricePerNight`
- Laden von Zimmerdetails
- Prüfen der Zimmerverfügbarkeit für einen Zeitraum
- Laden aller Buchungen eines Zimmers

Der `BookingService` übernimmt unter anderem:

- Erstellen von Buchungen
- Aktualisieren von Buchungen
- Loeschen von Buchungen
- Laden aller Buchungen
- Validierung des Buchungszeitraums
- Prüfung auf Buchungskonflikte
- Berechnung des Gesamtpreises anhand von Preis pro Nacht und Dauer
- Auflösen eines vorhandenen `userId` oder Erstellen/Finden eines Gasts anhand der E-Mail-Adresse

Der `UserService` übernimmt unter anderem:

- Laden aller Benutzer
- Laden eines einzelnen Benutzers
- Erstellen von Benutzern
- Aktualisieren von Benutzern
- Löschen von Benutzern
- Laden aller Buchungen eines Benutzers
- Validierung von E-Mail-Adressen
- Prüfung auf doppelte E-Mail-Adressen

#### DTOs

Die alte DTO-Klasse `RoomReadDto` wurde entfernt, da sie nicht zur API-Spezifikation gepasst hat.

Stattdessen wurden mehrere DTOs erstellt, die sich an den Response- und Request-Strukturen aus der API-Spezifikation orientieren:

- `RoomDto`
- `RoomListDto`
- `AvailabilityDto`
- `BookingDto`
- `BookingListDto`
- `BookingRequestDto`
- `GuestRequestDto`
- `UserDto`
- `UserListDto`
- `UserRequestDto`
- `PageDto`
- `ErrorDto`

Die DTOs wurden als Java `record`s umgesetzt, da sie in diesem Fall nur Daten transportieren und keine eigene Logik benötigen.

#### Repositories

Die vorhandenen Repositories wurden um benoetigte Spring-Data-JPA-Methoden erweitert.

Beispiele:

```java
findByRoomType_NameContainingIgnoreCase(...)
findByMailIgnoreCase(...)
existsByMailIgnoreCase(...)
findByRoom_Id(...)
findByGuest_Id(...)
```

Ausserdem wurde eine Query-Methode zur Prüfung von Buchungskonflikten ergänzt:

```java
existsByRoom_IdAndStatusNotAndArrivalDateLessThanAndDepartureDateGreaterThan(...)
```

Diese Methode prüft, ob es für ein Zimmer bereits eine aktive Buchung gibt, die sich mit dem gewünschten Zeitraum überschneidet.

#### Fehlerbehandlung

Es wurde eine einfache zentrale Fehlerbehandlung umgesetzt:

- `ApiException`
- `ApiExceptionHandler`

Dadurch können Services gezielt Fehler mit HTTP-Status werfen, z. B.:

```java
throw new ApiException(HttpStatus.NOT_FOUND, "No room with the given id found.");
```

Der `ApiExceptionHandler` wandelt diese Fehler in das in der API-Spezifikation vorgesehene Format um:

```json
{
  "errorMessage": "No room with the given id found."
}
```

### Wichtige Architekturentscheidung

Da das bestehende Datenmodell nicht exakt denselben Wortlaut wie die API-Spezifikation verwendet, wurde folgende Zuordnung getroffen:

- API `User` entspricht der bestehenden Entity `Guest`
- API `userId` entspricht `Guest.id`
- API `from` entspricht `Booking.arrivalDate`
- API `to` entspricht `Booking.departureDate`
- API-Zimmerdaten wie `name`, `description`, `pricePerNight`, `extras` und `imagePaths` werden aus `Room.roomType` abgeleitet

Dadurch musste das bestehende Entity-Modell nicht komplett umgebaut werden.

## 2. Backend Tests im Docker/Maven Container

### Beschreibung / Prompt

Ich habe Codex gebeten, die Änderungen trotzdem ueber die containerisierte Toolchain zu testen und sinnvolle Testcases zu ergänzen.

```text
the maven situation is that we use devcontainers where the necessary tooling is bundled within, i can handle what is installed on my machine myself but we agreed to use docker containers to abstract this for us. could you still manage to test your changes somehow? i tried a few basic api calls and they SEEM to work fine, but maybe you could also write testcases for them?
```

### Ergebnis

Es wurde ein neuer Integrationstest erstellt:

```text
Backend/src/test/java/team_teamarbeit/backend/ApiControllerIntegrationTests.java
```

Der Test verwendet `@SpringBootTest`, `@AutoConfigureMockMvc`, H2 und die echten Spring Beans. Dadurch werden nicht nur einzelne Methoden isoliert getestet, sondern der Request geht durch Controller, Service, Repository und JPA-Mapping.

Getestet werden unter anderem:

- `GET /rooms` inklusive Pagination, Filter, DTO-Shape, Extras und Bildern
- `GET /rooms/{id}/availability` bei überlappender Buchung
- `POST /bookings` mit Gastdaten und Preisberechnung
- `POST /bookings` mit Konfliktfall bei bereits belegtem Zimmer
- `POST /users`, `PUT /users/{id}`, `GET /users/{id}/bookings`, `DELETE /users/{id}`

Ausserdem wurde eine kleine Anpassung an den Services gemacht:

- Read-Services wurden mit `@Transactional(readOnly = true)` versehen, damit Lazy-Loaded JPA-Relationen wie `roomType.roomImages` und `roomType.roomExtras` stabil innerhalb einer Transaktion gelesen werden können.

### Testausführung

Die Tests wurden nicht mit lokal installiertem Maven ausgefuehrt, sondern mit dem Maven-Docker-Image:

```text
docker run --rm -v ${PWD}\Backend:/app -w /app maven:3.9-eclipse-temurin-21 mvn test
```

Ergebnis:

```text
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## 3. Refactoring der DTOs von Java Records zu Lombok POJOs

### Beschreibung / Prompt

Nachdem die ersten DTOs als Java `record`s erstellt wurden, habe ich Codex darauf hingewiesen, dass im Projekt bereits Lombok verwendet wird und dass daher ein Lombok-basierter POJO-Stil für die DTOs konsistenter wäre.

Zuerst habe ich Codex gebeten, vor einer Änderung die Auswirkungen einzuschätzen:

```text
we are using lombok in this project, which is why i think it would be better to use Lombok Powered POJO Style for DTO over java records. Before changing anything, consider the implications for all previous creations when doing so and state them to me
```

Danach habe ich der vorgeschlagenen Änderung zugestimmt und dabei festgelegt, dass die Services keinen Builder-Pattern-Zwang bekommen sollen und die API-Feldnamen gleich bleiben muessen:

```text
i agree with the proposed changes, make sure that the Services using the DTOs dont have to follow a builder pattern. Make sure the field names stay identical. Make no mistakes.
```

### Ergebnis

Codex hat alle zuvor erstellten DTOs von Java `record`s auf Lombok-basierte POJO-Klassen umgestellt.

Betroffen waren unter anderem:

- `RoomDto`
- `RoomListDto`
- `AvailabilityDto`
- `BookingDto`
- `BookingListDto`
- `BookingRequestDto`
- `GuestRequestDto`
- `UserDto`
- `UserListDto`
- `UserRequestDto`
- `PageDto`
- `ErrorDto`

Die DTOs verwenden nun folgenden Stil:

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExampleDto {
    private String exampleField;
}
```

Es wurde bewusst kein `@Builder` fuer die DTOs verwendet. Dadurch koennen die Services weiterhin Konstruktoren wie `new RoomDto(...)` oder `new BookingDto(...)` verwenden und müssen nicht auf Builder-Aufrufe umgestellt werden.

### Auswirkungen auf Services

Da Java `record`s andere Zugriffsmethoden verwenden als klassische JavaBeans, mussten einige Service-Zugriffe angepasst werden.

Vorher:

```java
request.roomId()
request.from()
request.guest()
```

Nachher:

```java
request.getRoomId()
request.getFrom()
request.getGuest()
```

Bei Boolean-Feldern wird die Lombok-/JavaBean-Konvention verwendet:

```java
request.isBreakfast()
```

Die Response-Erzeugung in den Services blieb weiterhin konstruktorbasiert. Es wurde also kein Builder-Pattern in den Services eingeführt.

### API-Kompatibilität

Die Feldnamen der DTOs wurden unverändert gelassen, damit sich die JSON-Struktur der API nicht ändert.

Beispiele:

- `roomId`
- `userId`
- `userMail`
- `pricePerNight`
- `imagePaths`
- `errorMessage`
- `available`
- `breakfast`

Dadurch bleiben die bestehenden Endpunkte und die Frontend-Erwartungen kompatibel.

### Testausführung

Nach dem Refactoring wurden die Backend-Tests erneut im Maven-Docker-Container ausgefuehrt:

```text
docker run --rm -v ${PWD}\Backend:/app -w /app maven:3.9-eclipse-temurin-21 mvn test
```

Ergebnis:

```text
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```
## 4. Korrektur der historischen User/Guest-Benennung

### Beschreibung / Prompt

Nachdem im Team geklärt wurde, dass es fachlich keine `User`, sondern nur `Guest` gibt, sollte die API- und Backend-Benennung bereinigt werden. Die bisherige Benennung als `User` in der API-Spezifikation war ein historischer Fehler.

```text
we have just clarified that the name "User" is essentially the entity "Guest" and the naming of guests as users in the API Spec is a historic mistake. There are only Guests and thus our DTOs need cleanup, aswell as the UserController and UserService. Check existing Guest classes in Repostitory and anywhere else for changes. Tell me what you intend to do before you change anything. Make no mistakes.
```

Nach der Analyse habe ich Codex erlaubt, die vorgeschlagenen Aenderungen umzusetzen:

```text
all your proposed changes look good to me, i allow you to follow through with them. Make no mistakes.
```

### Ergebnis

Die API-Schicht wurde von `User` auf `Guest` umgestellt.

Konkret wurden unter anderem folgende Dateien ersetzt oder umbenannt:

- `UserController` wurde durch `GuestController` ersetzt.
- `UserService` wurde durch `GuestService` ersetzt.
- `UserDto` wurde durch `GuestDto` ersetzt.
- `UserListDto` wurde durch `GuestListDto` ersetzt.
- `UserRequestDto` wurde entfernt, da `GuestRequestDto` bereits die korrekte Request-Struktur enthält.

Die bestehenden Guest-Klassen im Datenmodell blieben erhalten:

- `Guest`
- `GuestRepository`
- `Booking.guest`
- `BookingRepository.findByGuest_Id(...)`
- `BookingRepository.deleteByGuest_Id(...)`

### API-Aenderungen

Die REST-Endpunkte wurden angepasst:

```text
/users                  -> /guests
/users/{id}             -> /guests/{id}
/users/{id}/bookings    -> /guests/{id}/bookings
```

Die JSON-Felder wurden ebenfalls bereinigt:

```text
userId   -> guestId
userMail -> email
users    -> guests
```

Dadurch passt die API-Benennung nun zur bestehenden Domain-Entity `Guest`.

### Auswirkungen auf Buchungen

Im `BookingRequestDto` wird nun ein bestehender Gast mit `guestId` referenziert. Alternativ kann weiterhin ein `guest` Objekt direkt im Request mitgeschickt werden.

Beispiel:

```json
{
  "roomId": "...",
  "from": "2026-05-20",
  "to": "2026-05-23",
  "breakfast": true,
  "guestId": "...",
  "guest": null
}
```

Im `BookingDto` wird ebenfalls `guestId` statt `userId` zurückgegeben.

### Dokumentation und Tests

Die API-Spezifikation wurde von `Users` auf `Guests` umgestellt. Die Integrationstests wurden ebenfalls angepasst, sodass sie die neuen `/guests` Endpunkte und die neuen JSON-Felder pruefen.
