# AI Usage:

# Frontend

## Kontext

**Bereich:** Frontend-Entwicklung mit Vue 3, Ionic Framework und Pinia.  
**AI-Tool:** AI Assistant in JetBrains IDE (Modell: ChatGPT 5.5)

---

## 1. Zimmerübersicht aus React-Vorlage nach Vue/Ionic übertragen

|                    |                                                                                                                                                                                                                |
|--------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe**        | Eine vorhandene React-Seite aus unserem Figma-Paperprototype für die Zimmerübersicht sollte in `RoomsView.vue` nachgebaut werden. Die React-Vorlage enthielt Header, Zimmerkarten, Grid-Layout und Pagination. |
| **Prompt**         | Make RoomsView.vue look like this rooms react page: <code> |                                                                                                                                                    |
| **AI-Output**      | Erste Vue/Ionic-Version von `RoomsView.vue` mit statischen Zimmerdaten, Pagination, responsive Grid und Styling ähnlich der React-Vorlage.                                                                     |
| **Akzeptiert**     | ✅ Mit Modifikationen                                                                                                                                                                                           |
| **Modifikationen** | Layout, Cards, Preise, Buttons und Icons wurden mehrfach händisch und per prompts iterativ angepasst.                                                                                                          |
| **Begründung**     | Die AI lieferte eine gute Grundstruktur für die Zimmerübersicht, musste aber wegen leicht anderer Designwünsche (siehe unten) und Zusammenlegung zu gemeinsamen UI-Componenten nachgeschärft werden.           |

---

## 2. Responsive Room Cards und Desktop-Layout

| |                                                                                                                                                                        |
|---|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe** | Die Zimmerkarten sollten auf Desktop breiter dargestellt werden. Danach stellte sich heraus, dass die Karten abgeschnitten wurden.                                     |
| **Prompt** | Make the cards double the width on desktop.                                                                          |
| **AI-Output** | Vorschläge zur Änderung des Desktop-Layouts, zunächst mit breiteren horizontalen Cards und später wieder mit stabilerem Grid-Verhalten.                                |
| **Akzeptiert** | ✅ Mit Modifikationen                                                                                                                                                   |
| **Modifikationen** | Das Desktop-Layout wurde iterativ angepasst: zuerst breitere Cards, danach Korrektur wegen abgeschnittener Darstellung, dann drei Cards pro Desktop-Zeile mit Bild oben. |
| **Begründung** | Die AI half bei der Annäherung zum Paperprototype-Design.                                                                                                              |

---

## 3. Feature Pills mit Ionicons

| |                                                                                                                                                 |
|---|-------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe** | Die Feature Pills der Zimmerkarten sollten Icons mit Ionicons erhalten.                                                                         |
| **Prompt** | Add icons to the room feature pills using ionicons.                                                                                             |
| **AI-Output** | Erweiterung der Zimmerdaten um Feature-Objekte mit `label` und `icon`, Import passender Ionicons und Darstellung mit `IonIcon`.                 |
| **Akzeptiert** | ✅ Mit Modifikationen                                                                                                                            |
| **Modifikationen** | Die Icons wurden in der Card-Darstellung später in eine eigene Komponente ausgelagert.                                                          |
| **Begründung** | Die Lösung passte gut zur bestehenden Ionic/Vue-Struktur und ersetzte die ursprünglichen reinen Text-Pills durch visuell bessere Feature Pills. |

---

## 4. Preis- und Button-Darstellung in Room Cards

| |                                                                                                                                                                                        |
|---|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe** | Der Preis sollte nicht mehr als Badge auf dem Bild erscheinen, sondern über den Buttons. Zusätzlich sollte neben „Details ansehen“ ein Button „Verfügbarkeit prüfen“ angezeigt werden. |
| **Prompt** | Move the price over the details button and add a button to the right of the details buttons with "Verfügbarkeit prüfen".                                                               |
| **AI-Output** | Anpassung des Card-Templates: Preis in den Card-Footer verschoben, zwei Buttons nebeneinander auf größeren Screens, mobiles einspaltiges Layout.                                       |
| **Akzeptiert** | ✅ Mit Modifikationen                                                                                                                                                                   |
| **Modifikationen** | Später wurde der Preis weiter angepasst: große orange Preiszahl und kleiner grauer `/ Nacht` Text.                                                                                     |
| **Begründung** | Die AI setzte die gewünschte UX-Struktur schnell um. Feinschliff erfolgte iterativ anhand des Designs.                                                                                 |

---

## 5. Preisstyling und Divider unter Feature Pills

| |                                                                                                                                                                                                                              |
|---|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe** | Im Desktop-Layout sollten drei Cards pro Zeile angezeigt werden, das Bild wieder oben auf der Card erscheinen, eine Trennlinie unter den Feature Pills eingefügt und der Preis visuell hervorgehoben werden.                 |
| **Prompt** | Start from current state of roomsview.vue file - Show three cards in desktop layout, show image on top of card, show divider line below room feature pills, make price number big and orange and "per night" small and grey. |
| **AI-Output** | CSS-Anpassungen für drei Cards pro Zeile ab Desktop-Breite, Card-Layout mit Bild oben, Border unter den Feature Pills und neues Preis-Markup mit getrennten Spans für Preis und Zeitraum.                                    |
| **Akzeptiert** | ✅ Mit Modifikationen                                                                                                                                                                                                         |
| **Modifikationen** | Die Preiswerte wurden später als reine Zahlen verarbeitet, damit `€{{ room.price }}` und `/ Nacht` getrennt gestylt werden konnten.                                                                                          |
| **Begründung** | Die AI lieferte eine gute visuelle Struktur, die direkt zur gewünschten Card-Optik passte.                                                                                                                                   |

---

## 6. Ionic Button Hover/Active Bug analysiert

| |                                                                                                                                                                                                                                                                              |
|---|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe** | Die CSS-Regel `.white-button:hover:not(.active)` funktionierte nicht wie erwartet, aktive Pagination-Buttons hatten weiterhin einen hellen Hover-Hintergrund.                                                                                                                |
| **Prompt** | Why is the white-button still highlighted with a light bg color when active? The rule `.white-button:hover:not(.active)` doesn't work.                                                                                                                                       |
| **AI-Output** | Erklärung, dass kommaseparierte CSS-Selektoren eine Oder-Logik darstellen. Vorschlag, die Regel zu kombinieren: `.white-button:hover:not(:disabled):not(.active)`. Zusätzlich Hinweis auf Ionic CSS Custom Properties wie `--background-hover` und `--background-activated`. |
| **Akzeptiert** | ✅ Vollständig                                                                                                                                                                                                                                                                |
| **Begründung** | Die Ursache wurde korrekt erklärt und die Ionic-spezifische Lösung war nachvollziehbar.                                                                                                                                                                                      |

---

## 7. Komponenten nach Atomic Design ausgelagert

| | |
|---|---|
| **Aufgabe** | Subcomponents aus `RoomsView.vue` sollten in passende Component-Verzeichnisse ausgelagert werden. |
| **Prompt** | Split out subcomponents from RoomsView.vue and put them in the correct directories in components. |
| **AI-Output** | Vorschlag für neue Komponentenstruktur: |
| | **Atoms:** `RoomFeaturePill.vue` |
| | **Molecules:** `PaginationControls.vue` |
| | **Organisms:** `RoomCard.vue` |
| | **View:** `RoomsView.vue` nur noch für Page-Logik, Daten und Pagination-State |
| **Akzeptiert** | ✅ Mit Modifikationen |
| **Modifikationen** | `RoomFeaturePill.vue` wurde anschließend generischer in `IconPill.vue` umbenannt. |
| **Begründung** | Die Aufteilung entsprach dem Atomic-Design-Ansatz und machte `RoomsView.vue` deutlich schlanker. |

---

## 8. Generische IconPill-Komponente

| | |
|---|---|
| **Aufgabe** | `RoomFeaturePill` sollte generischer benannt werden, da die Komponente auch außerhalb von Zimmer-Features wiederverwendbar sein könnte. |
| **Prompt** | Name RoomFetaurePill more generic, as "IconPill". |
| **AI-Output** | Umbenennungsvorschlag zu `IconPill.vue`, Anpassung der CSS-Klassen und Aktualisierung der Imports in `RoomCard.vue`. |
| **Akzeptiert** | ✅ Vollständig |
| **Begründung** | Der neue Name ist allgemeiner und passt besser zu einer wiederverwendbaren Atom-Komponente. |

---

## 9. Backend-API-URL für Axios/Vite erklärt

| |                                                                                                                                                                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe** | Die App versuchte `/rooms` vom Frontend-Origin zu laden statt vom Backend. Es sollte erklärt werden, wie die Backend-URL gesetzt wird.                                                           |
| **Prompt** | It is trying to fetch /rooms from the frontend, not the backend - How do I set the backend api url?                                                                                              |
| **AI-Output** | Erklärung der Vite-Umgebungsvariable `VITE_API_BASE_URL` und Beispiel für `.env`: `VITE_API_BASE_URL=http://localhost:8080`. Hinweis, dass der Vite-Dev-Server danach neu gestartet werden muss. |
| **Akzeptiert** | ✅ Vollständig                                                                                                                                                                                    |
| **Begründung** | Die Erklärung passte zur vorhandenen Axios-Konfiguration mit `import.meta.env.VITE_API_BASE_URL`.                                                                                                |

---

## 10. RoomsView an Backend-Daten anbinden

| |                                                                                                                                                                                                         |
|---|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe** | `RoomsView.vue` sollte die Zimmerdaten nicht mehr statisch enthalten, sondern vom Backend laden.                                                                                                        |
| **Prompt** | Change RoomsView so that it fetches the rooms data from a backend. Here is the API spec: <text>.                                                                                   |
| **AI-Output** | Vorschlag zur Verwendung des bestehenden `room.store.ts`, Laden der Daten über `fetchRooms`, Mapping von Backend-Rooms in `RoomCardData`, Loading-, Error- und Empty-State in der View.                 |
| **Akzeptiert** | ✅ Mit Modifikationen                                                                                                                                                                                    |
| **Modifikationen** | Zuerst wurde eine API-Struktur mit `rooms` und `page` angenommen. Später stellte sich heraus, dass das Backend tatsächlich `items`, `pageNumber`, `pageSize`, `totalItems` und `totalPages` zurückgibt. |
| **Begründung** | Die grundsätzliche Store-Anbindung war hilfreich, musste aber an die tatsächliche Backend-Response angepasst werden.                                                                                    |

---

## 11. Frontend an tatsächliche Backend-Response angepasst

|                    |                                                                                                                                                                                                                                                                                                           |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe**        | Die Seite zeigte „Keine Zimmer gefunden“, obwohl das Backend Daten zurückgab. Die tatsächliche Response enthielt `items` statt `rooms` und `roomExtras` statt `extras`.                                                                                                                                   |
| **Prompts**        | Seite zeigt an "Keine Zimmer gefunden", obwohl in der Browserkonsole das vom Backend zurückkam: <json>. & Nicht normalisieren, Frontend an Backend anpassen.                                                                                                                                              |
| **AI-Output**      | Zunächst wurde Normalisierung im Endpoint vorgeschlagen. Danach wurde auf Wunsch des Users eine direkte Anpassung des Frontends an die Backend-Struktur vorgeschlagen: Models, Store und View sollten direkt mit `items`, `pageNumber`, `pageSize`, `totalItems`, `totalPages` und `roomExtras` arbeiten. |
| **Akzeptiert**     | ✅ Mit Modifikationen                                                                                                                                                                                                                                                                                      |
| **Modifikationen** | Der User entschied explizit, keine Normalisierung im Endpoint zu verwenden, sondern das Frontend an die Backend-Struktur anzupassen.                                                                                                                                                                      |
| **Begründung**     | Dadurch entspricht das Frontend direkt der tatsächlichen Backend-API und verwendet keine zusätzliche Mapping-Schicht im Endpoint.                                                                                                                                                                         |

---

## 12. RoomDetail-Seite aus React nach Vue/Ionic konvertieren

|                    |                                                                                                                                                                                                                          |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe**        | Eine vorhandene Zimmer-Detail-React-Seite aus unserem Figma-Paperprototype sollte in eine Vue/Ionic-Komponente umgewandelt werden, unter Wiederverwendung bestehender Komponenten aus `RoomsView.vue`.                                                    |
| **Prompt**         | Convert this react page into a vue page with ionic. Use ionic buttons and icons. I already have a general rooms page (see attached), use the same style and reuse components. `<RoomDetailPage.tsx>` `<RoomsView.vue>`   |
| **AI-Output**      | Vollständige `RoomDetailView.vue` mit `IonButton`, `IonIcon`, `PageTemplate`, `IconPill`, Bild-Banner, Preis-Block, Feature-Grid, Back-Button und Routing via `:router-link`. Hinweise zu `@JoinTable`, Routing-Setup und Auslagerung von `getFeatureIcon` in ein Composable. |
| **Akzeptiert**     | ✅ Mit Modifikationen                                                                                                                                                                                                     |
| **Modifikationen** | Layout und Komponenten wurden iterativ angepasst; `getFeatureIcon` in `getIonicIcon.ts` ausgelagert, `PaddedPageTemplate` statt `PageTemplate` verwendet.                                                               |
| **Begründung**     | Die AI lieferte eine solide Grundstruktur, die direkt an die bestehende Komponentenarchitektur angepasst werden konnte.                                                                                                  |

---

## 13. Gemeinsame Subkomponenten aus RoomsView und RoomDetailView extrahieren

|                    |                                                                                                                                                                                                                          |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe**        | Wiederverwendbare Subkomponenten aus beiden Views sollten nach Atomic Design in `atoms`, `molecules`, `organisms` und `templates` aufgeteilt werden.                                                                     |
| **Prompt**         | Das sind beide Komponenten. Kannst du gemeinsame Subkomponenten extrahieren und sie in einen components-Ordner (mit atoms, molecules, organisms und templates Subfoldern) aufteilen? `<RoomDetailView.vue>` `<RoomsView.vue>` |
| **AI-Output**      | Neue Komponenten: `StateMessage.vue` (atom), `PriceDisplay.vue` (atom), `RoomFeatureList.vue` (molecule), `RoomImageBanner.vue` (molecule), `BackButton.vue` (molecule). Aktualisierte `RoomDetailView.vue` mit allen extrahierten Komponenten und finale Ordnerstruktur. |
| **Akzeptiert**     | ✅ Mit Modifikationen                                                                                                                                                                                                     |
| **Modifikationen** | `StateMessage` wurde auch in `RoomsView.vue` eingesetzt. `RoomImageBanner` wurde später durch `RoomImageCarousel` ersetzt.                                                                                              |
| **Begründung**     | Die Aufteilung entsprach dem bereits verwendeten Atomic-Design-Ansatz und reduzierte Codeduplikation zwischen den beiden Views deutlich.                                                                                 |

---

## 14. Bild-Karussell mit Pfeil-Navigation für RoomDetailView

|                    |                                                                                                                                                                                                                          |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe**        | Die Detailseite sollte mehrere Bilder aus `imagePaths` anzeigen können, mit Links/Rechts-Pfeilen und Dot-Navigation.                                                                                                    |
| **Prompt**         | Die Roomdetail-Seite soll mehrere Bilder aus dem found.imagePaths array anzeigen, mit Pfeilen für links und rechts. Das ist der aktuelle Stand: `<RoomDetailView.vue>`                                                  |
| **AI-Output**      | Neue Komponente `RoomImageCarousel.vue` mit `chevronBackOutline`/`chevronForwardOutline` Buttons, Dot-Indikatoren, zyklischer Navigation und Fallback auf ein einzelnes Bild. Anpassung von `RoomDetailView.vue` und dem `room` computed um ein `images`-Array zu übergeben. |
| **Akzeptiert**     | ✅ Vollständig                                                                                                                                                                                                            |
| **Modifikationen** | Keine – Komponente und computed wurden direkt übernommen.                                                                                                                                                               |
| **Begründung**     | Die Lösung war vollständig und passte zur bestehenden Komponentenstruktur. Pfeile werden nur bei mehr als einem Bild angezeigt.                                                                                         |

# Backend

## Kontext

**Bereich:** Backend-Entwicklung mit Java Spring Boot, JPA/Hibernate und H2 In-Memory-Datenbank.  
**AI-Tool:** Claude (Modell: Sonnet 4.6) & AI Assistant in JetBrains IDE (Modell: ChatGPT 5.5)

---

## 1. Figma-Prototyp-Daten in DB-Schema überführen und als H2-Seed laden

|                    |                                                                                                                                                                                                                          |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe**        | Die statischen Zimmerdaten aus dem Figma-Paperprototype sollten in das bestehende Datenbankschema (ROOM_TYPES, ROOM_EXTRAS, ROOM_TYPE_EXTRAS, ROOMS) überführt und als Standard-Seed für H2 eingerichtet werden.         |
| **Prompt**         | Can you convert the following data into this db scheme? How do I add it to a Java spring app so this data gets loaded into H2 by default? `<rooms data from figma prototype>` `<db-schema>`                             |
| **AI-Output**      | SQL-INSERT-Statements für alle Tabellen (ROOM_TYPES, ROOM_EXTRAS, ROOM_TYPE_EXTRAS, ROOMS) als `data.sql`, Erklärung der Spring-Konvention zur automatischen Erkennung von `data.sql` aus `src/main/resources/`, sowie notwendige `application.properties`-Einträge inkl. `spring.jpa.defer-datasource-initialization=true`. |
| **Akzeptiert**     | ✅ Mit Modifikationen                                                                                                                                                                                                     |
| **Modifikationen** | Der Join-Table-Name `room_type_extras` stimmte nicht mit dem von Hibernate auto-generierten Namen überein. Die `@ManyToMany`-Relation in `RoomType` musste mit `@JoinTable(name = "room_type_extras", ...)` explizit annotiert werden, damit `data.sql` die korrekte Tabelle befüllt. |
| **Begründung**     | Die AI lieferte eine vollständige Grundstruktur für das Seeding. Der Fehler `Table "ROOM_TYPE_EXTRAS" not found` wurde durch Analyse der Entity-Klasse und gezielte Nachfrage zur `@JoinTable`-Annotation behoben.       |

---

## 2. Leerer imagePaths-Array im /rooms Endpoint

|                    |                                                                                                                                                                                                                          |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe**        | Der `/rooms`-Endpoint lieferte einen leeren `imagePaths`-Array, obwohl Bild-URLs in `data.sql` vorhanden waren.                                                                                                         |
| **Prompt**         | Passt das data.sql Skript zum Code? Speziell das Format der angegebenen Bilder als URLs? Weil das Backend liefert einen leeren imagePaths array, wenn man es per /rooms endpoint abfragt.                                |
| **AI-Output**      | Analyse ergab, dass die URLs korrekt in `cover_image_path` von `room_types` geschrieben wurden, das DTO `imagePaths` aber aus der `room_images`-Relation befüllt. Da keine Einträge in `room_images` existierten, war das Array leer. Lösung: zusätzliche INSERT-Statements für `room_images` in `data.sql`. |
| **Akzeptiert**     | ✅ Vollständig                                                                                                                                                                                                            |
| **Modifikationen** | Keine – die room_images-Inserts wurden direkt übernommen.                                                                                                                                                               |
| **Begründung**     | Die AI identifizierte korrekt die Diskrepanz zwischen `cover_image_path` in `room_types` und der DTO-Logik, die `roomImages` als Relation erwartet.                                                                     |

---

## 3. coverImagePath ins RoomTypeDto aufnehmen

|                    |                                                                                                                                                                                                                          |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Aufgabe**        | Das `RoomTypeDto` sollte zusätzlich das `coverImagePath`-Feld aus der `room_types`-Tabelle direkt im Response zurückgeben.                                                                                              |
| **Prompt**         | Can you also add the cover image to the dto?                                                                                                                                                                             |
| **AI-Output**      | Erweiterung von `RoomTypeDto` um das Feld `coverImagePath` sowie Mapping im Builder über `roomType.getCoverImagePath()`.                                                                                                |
| **Akzeptiert**     | ✅ Vollständig                                                                                                                                                                                                            |
| **Modifikationen** | Keine – die DTO-Erweiterung wurde direkt übernommen.                                                                                                                                                                    |
| **Begründung**     | Saubere Ergänzung ohne Seiteneffekte, die das Frontend direkt mit einem Cover-Bild pro Zimmertyp versorgt.                                                                                                              |