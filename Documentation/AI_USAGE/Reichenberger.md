# AI Usage:

## 1. Figma-Make-Prototyp für die Hotel-App

## Ziel

Für das Projekt **Hotel Booking Interface** sollte ein erster klickbarer UI-Prototyp für eine **Mobile-First-Hotel-App** erstellt werden. Dafür habe ich KI verwendet, um aus der vorhandenen Projektspezifikation einen geeigneten Prompt für **Figma Make** erstellen zu lassen.

## Verwendung von ChatGPT

Ich habe ChatGPT folgenden Prompt gegeben:

```text
"Baue mir mit dieser Projektspezifikation einen Prompt, den ich Figma Make übergeben 
kann, sodass wir ein UI-Design für die Mobile-First-Hotel-App bekommen. Es sollen Vue.js mit Ionic Framework für das Frontend verwendet werden und Pinia für State Management. Project: Hotel Booking Interface
ProjektSpezifikation2026.pdf"
```

## Weitergabe an Figma Make

ChatGPT hat aus meiner Projektspezifikation einen ausführlichen und strukturierten Prompt für **Figma Make** erstellt. Diesen von ChatGPT generierten Prompt habe ich anschließend kopiert und direkt in **Figma Make** eingefügt:

```text
 Erstelle ein modernes, mobile-first UI-Design für eine Hotel Booking App namens **„Boutique Hotel Technikum“**.

 Das Design soll für eine Single Page Application konzipiert sein, die mit **Vue.js 3**, **Ionic Framework**, **Axios** und **Pinia** umgesetzt wird. Bitte gestalte die Oberfläche so, dass sie sich gut in 
 Ionic-Komponenten übersetzen lässt, z. B. `IonPage`, `IonHeader`, `IonContent`, `IonToolbar`, `IonCard`, `IonButton`, `IonModal`, `IonToast`, `IonInput`, `IonSelect`, `IonCheckbox`, `IonDatetime`, `IonTabs` und `IonList`.

 Die App soll primär für Smartphones gestaltet werden, aber auch auf Desktop-Bildschirmen gut funktionieren. Erstelle daher ein responsives Design mit Mobile-First-Layouts und Desktop-Varianten.


Zielgruppe

 Hotelgäste, die sich über das Hotel informieren, Zimmer ansehen, Verfügbarkeit prüfen und ein Zimmer buchen möchten.

---

Design-Stil

 Modern, hochwertig, ruhig, boutique-hotel-artig, vertrauenswürdig, elegant, aber nicht überladen.

 Verwende warme neutrale Farben, gute Lesbarkeit, klare Hierarchie, großzügige Abstände, runde Cards und hochwertige Bildflächen.

 Das Design soll professionell, nachhaltig und erweiterbar wirken.

---

Technische und strukturelle Vorgaben

 - Vue.js 3 Frontend
 - Ionic Framework UI-Konzept
 - Pinia für State Management
 - Axios für API-Kommunikation
 - Atomic Design Struktur: Atoms, Molecules, Organisms, Templates, Pages
 - Mobile first, Desktop responsive
 - Clean UI, klare User Flows, gute Fehlermeldungen
 - Barrierearme Gestaltung mit ausreichenden Kontrasten und gut lesbaren Formularen
 - Design soll für spätere Erweiterungen skalierbar sein
 - Backend ist Spring Boot mit MySQL, aber hierfür bitte nur die Frontend-UX berücksichtigen

---

Gewünschtes UI-Konzept

 "Erstelle ein vollständiges UI-Konzept mit folgenden Screens, Komponenten und Zuständen:

---

1. Landing Page / Hotel Website

 **Ziel:** Gäste sollen das Boutique Hotel Technikum kennenlernen.

 **Inhalte:**

 - Hero-Bereich mit großem Hotelbild
 - Hotelname **„Boutique Hotel Technikum“**
 - Kurzer Claim, z. B. **„Smart comfort in the heart of Vienna“**
 - Call-to-Action **„Zimmer entdecken“**
 - Sektionen: Über das Hotel, Ausstattung, Lage, Galerie, Kontakt
 - Navigation zu Landing Page, Zimmer, Buchung, About, Impressum
 - Mobile Navigation als Bottom Tabs oder Burger-Menü
 - Desktop Navigation als horizontale Top Navigation

---

2. About Page

 **Ziel:** Das Hotel vorstellen.

 **Inhalte:**

 - Story des Hotels
 - Werte: Komfort, Nachhaltigkeit, moderne Ausstattung, persönliche Betreuung
 - Bild/Text-Layout
 - Kontakt-CTA

---

3. Imprint Page

 **Ziel:** Statische rechtliche Seite.

 **Inhalte:**

 - Hotelname
 - Adresse
 - Kontaktinformationen
 - Rechtliche Pflichtinformationen als gut lesbare Textblöcke

---

 4. Room Overview / Zimmerübersicht

 **Ziel:** Gäste sollen Zimmer ansehen und vergleichen können.

 **Inhalte je Zimmerkarte:**

 - Zimmerbild
 - Titel
 - Kurzbeschreibung
 - Preis pro Nacht als visuelles Element
 - Extras mit Icons, z. B. WLAN, Frühstück, Klimaanlage, TV, Balkon, Workspace
 - Button **„Details ansehen“**
 - Button **„Verfügbarkeit prüfen“**
 - Maximal 5 Zimmer beim ersten Laden anzeigen
 - Attraktive Darstellung auch bei ungerader Anzahl an Zimmern
 - Pagination mit Button-Gruppe: Zurück, 1, 2, 3, Weiter
 - Mobile: vertikale Cards
 - Desktop: Grid mit 2–3 Spalten

---

5. Room Detail Page

 **Ziel:** Ein ausgewähltes Zimmer genauer ansehen.

 **Inhalte:**

 - Großes Zimmerbild mit einheitlicher Bilddarstellung trotz unterschiedlicher Bildgrößen
 - Titel
 - Beschreibung
 - Extras mit Icons
 - Preis
 - Zimmerdetails
 - CTA **„Verfügbarkeit prüfen“**
 - CTA **„Zurück zur Übersicht“**

---

6. Availability Check

 **Ziel:** Gäste sollen prüfen, ob ein Zimmer im gewünschten Zeitraum verfügbar ist.

 **Inhalte:**

 - Ausgewähltes Zimmer als kompakte Summary Card
 - Date Dialog / Date Picker für Anreise und Abreise
 - Visuelle Darstellung des gewählten Zeitraums
 - Button **„Verfügbarkeit prüfen“**
 - Ladezustand während API-Prüfung
 - Erfolgszustand: Zimmer verfügbar, Button **„Jetzt buchen“**
 - Fehlerzustand: Zimmer nicht verfügbar, Alternative anzeigen

 **Validierungsfehler:**

 - Anreise fehlt
 - Abreise fehlt
 - Abreise liegt vor Anreise
 - Zeitraum ist ungültig
 - API nicht erreichbar

 **Feedback:**

 - Toasts
 - Inline-Messages
 - Alert Cards

---

7. Booking Form

 **Ziel:** Gäste sollen ein verfügbares Zimmer buchen können.

 **Pflichtfelder:**

 - Vorname
 - Nachname
 - E-Mail-Adresse
 - E-Mail-Adresse bestätigen
 - Frühstück ja/nein
 - Buchungszeitraum
 - Zimmerauswahl

 **Anforderungen:**

 - Formular mit klarer Validierung
 - E-Mail muss gültig sein
 - E-Mail und Bestätigungs-E-Mail müssen übereinstimmen
 - Pflichtfelder müssen klar markiert sein
 - Frühstück als Toggle oder Checkbox
 - Buchungszusammenfassung am Ende des Formulars
 - Button **„Buchung überprüfen“**

---

8. Booking Review Screen

 **Ziel:** Gäste sollen ihre Angaben vor dem Absenden prüfen können.

 **Inhalte:**

 - Zimmerkarte mit Bild, Titel, Extras und Beschreibung
 - Buchungszeitraum
 - Persönliche Daten
 - Frühstücksauswahl
 - Preiszusammenfassung, falls möglich
 - Button **„Daten bearbeiten“**
 - Button **„Buchung verbindlich absenden“**
 - Ladezustand beim Absenden
 - Fehlerzustand bei fehlgeschlagener Buchung

---

9. Booking Confirmation Screen

 **Ziel:** Nach erfolgreicher Buchung soll der Gast alle wichtigen Informationen erhalten.

 **Inhalte:**

 - Klare Erfolgsmeldung mit Icon
 - Buchungsnummer als Platzhalter
 - Buchungszeitraum
 - Hotelzimmer inklusive Bild, Titel, Extras und Beschreibung
 - Persönliche Daten des Gasts
 - Frühstücksauswahl
 - Hinweise zu Anreise und Check-in
 - Adresse des Hotels
 - Anfahrtsbereich mit Google Maps Platzhalter oder Karten-Komponente
 - Öffentliche Verkehrsmittel / Zugverbindung als Informationsblock
 - Kontaktoptionen: Telefon, E-Mail, Website
 - Button **„Zur Startseite“**
 - Button **„Buchung drucken“**
 - A4-print-freundliche Variante der Bestätigung

---

10. Error, Empty und Loading States

 Erstelle eigene UI-Zustände für:

 - Zimmerliste lädt
 - Keine Zimmer gefunden
 - Verfügbarkeit wird geprüft
 - API-Fehler
 - Formularvalidierung
 - Buchung fehlgeschlagen
 - Buchung erfolgreich
 - Kein Internet / Server nicht erreichbar

---

11. Navigation und App-Struktur

 Erstelle eine klare Informationsarchitektur:

 - Home
 - Zimmer
 - Verfügbarkeit
 - Buchung
 - Bestätigung
 - About
 - Impressum

Mobile Navigation

 - Bottom Navigation oder gut nutzbares Burger-Menü
 - Primäre Aktionen immer gut erreichbar

Desktop Navigation

 - Top Navigation
 - Zentrierter Content mit maximaler Breite
 - Grid-Layouts für Zimmer und Informationsbereiche

---

12. Component System nach Atomic Design

 Bitte erstelle auch eine kleine Design-System-Übersicht mit Komponenten:

Atoms

 - Buttons
 - Inputs
 - Labels
 - Icons
 - Badges
 - Error Text
 - Loading Spinner

Molecules

 - Date Range Picker
 - Form Field Group
 - Extra Icon Label
 - Pagination Button Group
 - Room Price Badge
 - Contact Info Row

Organisms

 - Room Card
 - Room Detail Header
 - Booking Form
 - Booking Summary
 - Availability Status Card
 - Confirmation Details Card
 - App Header
 - Mobile Navigation

Templates

 - Static Content Page
 - Room Listing Page
 - Form Page
 - Confirmation Page

Pages

 - Landing Page
 - About
 - Imprint
 - Room Overview
 - Room Detail
 - Availability Check
 - Booking Form
 - Booking Review
 - Booking Confirmation

---
13. Pinia Store Awareness

 Das UI soll erkennen lassen, welche Zustände in Pinia gespeichert werden könnten:

 - `roomsStore`: Zimmerliste, ausgewähltes Zimmer, Pagination
 - `availabilityStore`: Anreise, Abreise, Verfügbarkeitsstatus, Ladezustand, Fehler
 - `bookingStore`: Gästedaten, Frühstück, Review-Daten, Buchungsergebnis
 - `uiStore`: Toasts, Modals, Navigation, Loading States

---

Einschränkung

 Bitte erstelle keine Backend-Ansichten.

 Konzentriere dich auf das Frontend-Design und den kompletten Gast-Flow.

---

Output-Wunsch

 Erstelle ein klickbares, sauberes UI-Design mit Mobile-First Screens und passenden Desktop-Varianten.

 Benenne Frames und Komponenten klar, sodass Entwicklerinnen und Entwickler sie später einfach in Vue.js mit Ionic Framework nachbauen können.

 Nutze realistische Platzhalterdaten, Beispielzimmer und Beispielbilder.

 Achte auf klare UX, gute Lesbarkeit, elegante Gestaltung und vollständige User Flows von der Hotelpräsentation bis zur Buchungsbestätigung."
```

Mit Figma Make wurde daraus ein erster klickbarer Prototyp für die Hotel-App erstellt. Der Prototyp sollte die wichtigsten Screens und User Flows der Anwendung abbilden, insbesondere:

- Landing Page / Hotel Website
- Zimmerübersicht
- Zimmerdetails
- Verfügbarkeitsprüfung
- Buchungsformular
- Review-Screen vor dem Absenden
- Buchungsbestätigung
- statische Seiten wie About und Impressum

Der KI-generierte Figma-Make-Prompt wurde somit als Grundlage verwendet, um schnell einen ersten visuellen Entwurf und klickbaren Prototyp für die weitere Projektarbeit zu erhalten.

## WICHTIG!
### Die Screenshots vom Figma Make Prototyp und der Link zum klickbaren Figma Make Prototyp befinden sich in Paperprototype.md


# Frontend-Entwicklung:

## AI Usage Dokumentation Sitzung: 2026-05-28

**Tool:** GitHub Copilot (Chat-Agent in JetBrains IDE)


### 1. About-Page: React → Vue/Ionic Portierung

| |                                                                                                                                                                                                                                                |
|---|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Prompt/Aufgabe** | "Baue diese About Us Page (React) nach in Vue mit Ionic, achte auf einen Mobile First Ansatz." – React-Komponente mit Lucide-Icons, Values-Grid und Kontakt-CTA als Vorlage übergeben                                                          |
| **AI-Output** | `About.vue` mit Ionic-Komponenten, Ionicons statt Lucide, scoped CSS mit Mobile-First-Ansatz                                                                                                                                          |
| **Akzeptiert** | ✅ Mit Modifikationen – Grundstruktur übernommen                                                                                                                                                                                                |
| **Modifikationen** | Mehrfach angepasst: Dark-Mode-Hintergrund gefixt (schwarz → weiß), Padding für Navbar-Overlap hinzugefügt, Navbar-Farbe von blau auf weiß geändert, Navabr-Größe angepasst. Navbar-Anordnung angepasst.                                        |
| **Begründung** | Mit Modifikationen übernommen weil es am Anfang probleme mit der Hintergrundfarbe gab, welche Standardmäßig schwarz ist in Ionic, Navbar hat content überdeckt, hatte die falsche Farbe und Größe und eine seltsame Anordnung der Nav-Elemente |


### 2. Atomic Design Komponentenstruktur

| |                                                                                                                                           |
|---|-------------------------------------------------------------------------------------------------------------------------------------------|
| **Prompt/Aufgabe** | "Organisiere ab jetzt die einzelnen Components in einem Components-Ordner mit Unterordnern für atoms, molecules, organisms und templates" |
| **AI-Output** | Refactoring der bestehenden Views in wiederverwendbare Komponenten:                                                                       |
| | **Atoms:** `AppIcon.vue`, `SectionHeader.vue`                                                                                             |
| | **Molecules:** `ContactCard.vue`,`ValueCard.vue`                                                                                          |
| | **Organisms:** Keine                                                                                                                      |
| | **Templates:** `PaddedPageTemplate.vue`                                                                                                   |
| **Akzeptiert** | ✅ Vollständig                                                                                                                             |
| **Begründung** | Sehr gute aufteilung der Komponenten in Atoms, Molecules, Organisms und Templates                                                         |

### 3. HomePage: React → Vue/Ionic Portierung

| |                                                                                                                                                                                                                                                                                                                                                                                      |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Prompt/Aufgabe** | "Baue diese Homepage nach in Vue mit Ionic, achte auf einen Mobile First Ansatz." – React Landing Page mit Hero, Features, About-Sektion und <br> CTA übergeben."                                                                                                                                                                                                                    |
| **AI-Output** | `HomePage.vue` mit Hero-Sektion (Fullscreen-Bild + Gradient-Overlay), Features-Grid, About-Bereich und CTA. <br>`FeatureCard.vue`, `FeatureSection.vue`,  `HeroSection.vue`,  `CtaSection.vue`, `PageTemplate`                                                                                                                                                                       |
| **Akzeptiert** | ✅ Mit Modifikationen                                                                                                                                                                                                                                                                                                                                                                 |
| **Modifikationen** |                                                                                                                                                                                                                                                                                                                                                                                      |
| **Begründung** | Page Layout hat gut funktioniert zu generieren, jedoch mussten einige Kleinigkeiten geändert werden, vor allem was das Container Padding und <br> die Margins anging, da das `PaddedPageTemplate` eine zu hohes padding hatte sodass das Bild in der HeroSection Abstand zur NavBar hatte und dies seltsam aussah.<br> `PageTemplate` wurde dann erstellt um dieses Problem zu lösen |


## Bugfixes (iterativ)

| |                                                                                                                                             |
|---|---------------------------------------------------------------------------------------------------------------------------------------------|
| **Prompt/Aufgabe** | Mehrere Inline Bugfix-Anfragen: teilweise schwarzer Hintergrund im Body, Navbar nicht responsive, Mobile-Menu-Hintergrund hat falsche Farbe |
| **AI-Output** | Anpassungen in `App.vue` (NavBar in auf Mobile geräten sah seltsam aus, falsche Farbe der Navbar auf Mobile Geräten)                        |
| **Akzeptiert** | ✅ Schrittweise akzeptiert nach Überprüfung im Code und im Browser                                                                           |
| **Begründung** | Ionic-spezifische Probleme (CSS Properties und wie diese)                                                                                   |

## Zusammenfassung

| Metrik | Wert                                      |
|---|-------------------------------------------|
| **AI-Tool** | GitHub Copilot (Claude-Opus 4.6)          |
| **Anzahl Interaktionen** | 9 Prompts                                 |
| **Generierter Code** | 13 Dateien bearbeitet (Views, Components) |
| **Vollständig akzeptiert** | ~67%                                      |
| **Mit Modifikationen akzeptiert** | ~33%                                      |
| **Abgelehnt** | 0%                                        |

**Hauptsächliche Modifikationen:** Styling-Fixes (Dark Mode, Hintergrundfarbe), Navigation (Tab-Bar → Burger+Horizontal), Scrolling-Verhalten. Diese entstanden durch iteratives Testing im Browser und Feedback an die AI.

**Eigenleistung:** Architektur-Entscheidungen (Atomic Design Anforderung), UX-Anforderungen (Mobile-First, Burger vs. Horizontal Nav), Code-Review und Testing im Browser, Bugfinding und Fehlerbeschreibung.


# Allgemeine Erklärungen im Backend (Java/SpringBoot)

## Session vom 09.06.2026: 

Da ich zuvor noch nie mit Java/Spring Boot gearbeitet habe, habe ich den bereits von
meinen Kollegen erstellten Code analysieren und erklären lassen, damit ich dann selbstständig die Backend-Route Book Room erstellen kann.

## Prompt: 
```text
Was bewirken diese Annotations und wozu werden diese in Java bzw. SpringBoot verwendet? 

@Entity(name = "Bookings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "reference_key", unique = true)
    private String referenceKey;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "breakfast")
    private boolean breakfast;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookingStatusType status;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
```

## Ergebnis:
```text
Annotationen sind Metadaten, die mit @ beginnen. 
Frameworks wie Spring Boot, Hibernate/JPA und Lombok lesen sie und erzeugen daraus Verhalten oder zusätzlichen Code.

Klassen-Annotationen:
@Entity(name = "Bookings")
Markiert die Klasse als JPA-Entity. Hibernate kann Booking-Objekte dadurch in der Datenbank speichern.
name bezeichnet primär den Entity-Namen für JPQL. Für einen expliziten Tabellennamen wäre normalerweise Folgendes besser:


@Entity
@Table(name = "bookings")


@Getter und @Setter
Lombok generiert Getter und Setter für alle Felder.

@Builder
Lombok ermöglicht das Builder-Pattern:
java

Booking booking = Booking.builder()
    .arrivalDate(LocalDate.now())
    .breakfast(true)
    .build();

@NoArgsConstructor
Erzeugt einen Konstruktor ohne Parameter. JPA benötigt diesen, um Entities zu erstellen.

@AllArgsConstructor
Erzeugt einen Konstruktor, der alle Felder als Parameter erwartet.

Datenbank-Annotationen:

@Id
Markiert id als Primärschlüssel der Entity.

@GeneratedValue(strategy = GenerationType.UUID)
Hibernate erzeugt beim Speichern automatisch eine UUID.

Column(name = "...")
Legt fest, welcher Datenbankspalte das Java-Feld entspricht:
java

private LocalDate arrivalDate;

wird beispielsweise auf arrival_date abgebildet.

@Column(unique = true)
Erstellt eine Eindeutigkeitsbedingung. Zwei Buchungen dürfen nicht denselben reference_key haben.

@Enumerated(EnumType.STRING)
Speichert den Enum als lesbaren Text, beispielsweise:

CONFIRMED

Ohne diese Einstellung könnte der Enum als Zahl gespeichert werden, was bei Änderungen an der Enum-Reihenfolge problematisch wäre.

Beziehungen
@ManyToOne
Definiert eine Viele-zu-eins-Beziehung. Viele Buchungen können demselben Gast oder Zimmer zugeordnet sein.

@JoinColumn(name = "guest_id")
Verwendet guest_id als Fremdschlüssel zur Gast-Tabelle.

@JoinColumn(name = "room_id")
Verwendet room_id als Fremdschlüssel zur Zimmer-Tabelle.

Spring Boot erkennt die Entity beim Start. Hibernate erstellt beziehungsweise aktualisiert anschließend die Tabellenstruktur und übersetzt Repository-Aufrufe in SQL. Lombok reduziert dagegen lediglich den manuell zu schreibenden Java-Code.
```

## Prompt: 

```text
Wie funktioniert das Exception handling? bzw wo wird der Exception Handler aufgerufen?
```
## Ergebnis
```text
Der Exception Handler wird nicht manuell aufgerufen. Spring ruft ihn automatisch auf, sobald eine Exception aus einem Controller beziehungsweise Service bis zur Controller-Schicht hochgereicht wird.

Ablauf:

HTTP Request
→ BookingController.bookRoom()
→ BookingService.bookRoom()
→ Exception wird geworfen
→ Spring fängt sie ab
→ passender @ExceptionHandler wird gesucht
→ HTTP-Fehlerantwort wird erzeugt
Beispielsweise im Service:

throw new BookingConflictException(
    "The requested room is not available"
);
Die Exception läuft zurück durch den Controller. Wegen @RestControllerAdvice findet Spring automatisch:

@ExceptionHandler(BookingConflictException.class)
@ResponseStatus(HttpStatus.CONFLICT)
public ErrorDto handleConflict(BookingConflictException ex) {
    return new ErrorDto(ex.getMessage());
}
Der Handler wird intern vom Spring MVC DispatcherServlet beziehungsweise dessen Exception-Resolver aufgerufen. Der Controller benötigt daher kein try/catch.
```

## Prompt

```text
Wie kann ich in Java/Spring Boot in einem Service, der Business Logic beinhaltet, entweder alle Datenbankoperationen ausführen oder keine, wenn eine fehlschlägt?
```

## Ergebnis

```text
In Spring Boot machst du das mit Transaktionen, typischerweise über @Transactional.

Damit gilt: Entweder werden alle Datenbankoperationen erfolgreich committed, oder bei einem Fehler wird alles zurückgerollt.

Beispiel:

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public OrderService(
            OrderRepository orderRepository,
            PaymentRepository paymentRepository
    ) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void createOrder(Order order, Payment payment) {
        orderRepository.save(order);

        paymentRepository.save(payment);

        // Wenn hier oder davor eine RuntimeException fliegt,
        // werden beide save()-Operationen zurückgerollt.
    }
}

Wichtig: @Transactional sollte meistens auf der Service-Methode liegen, nicht auf einzelnen Repository-Methoden. Der Service bildet die Business-Transaktion ab.

Standardmäßig macht Spring einen Rollback bei:

RuntimeException
Error

Also zum Beispiel:

@Transactional
public void transferMoney(Account from, Account to, BigDecimal amount) {
    from.withdraw(amount);
    to.deposit(amount);

    accountRepository.save(from);
    accountRepository.save(to);

    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Amount must be positive");
    }
}

Wenn die Exception fliegt, werden beide save()-Operationen nicht dauerhaft gespeichert.

Für checked Exceptions musst du Rollback explizit angeben:

@Transactional(rollbackFor = Exception.class)
public void doSomething() throws Exception {
    repository.save(...);

    if (someProblem) {
        throw new Exception("Fehler");
    }
}

Achte außerdem auf diese typischen Stolperfallen:

@Transactional
public void outerMethod() {
    innerMethod(); // funktioniert NICHT wie erwartet, wenn innerMethod im selben Service ist
}

@Transactional
public void innerMethod() {
    // ...
}

@Transactional funktioniert über Spring-Proxies. Ein Methodenaufruf innerhalb derselben Klasse wird daher nicht vom Proxy abgefangen. Lege die Transaktion am besten direkt auf die öffentliche Service-Methode, die von außen aufgerufen wird.

Typisches Muster:

@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderRequest request) {
        orderService.createOrder(request);
        return ResponseEntity.ok().build();
    }
}
@Service
public class OrderService {

    @Transactional
    public void createOrder(CreateOrderRequest request) {
        // Business Logic
        // mehrere DB-Operationen
        // bei Fehler: Rollback
    }
}

Kurz gesagt: Verwende @Transactional auf deiner Service-Methode, wirf bei Fehlern Exceptions, und fange diese Exceptions nicht innerhalb der Transaktion ab, ohne sie erneut zu werfen.
```

### Diese Prompts haben mir dabei geholfen die Eigenheiten/Unterschiede und Gemeinsamkeiten zu mir bereits bekannten Frameworks wie: 
- ### .NET
- ### ASP.NET
- ### EF Core


### und weiteren besser zu verstehen. 
### Dadurch konnte ich bereits bekannte Paradigmen auch in Java/Spring Boot Anwenden, da die Prinzipien dahinter sehr ähnlich sind und sich meistens nur in der Syntax/Art der Implementierung unterscheiden.

# Fazit: 

- ### Besonders gut funktioniert die Verwendung von generativer KI, wenn man bereits ein sehr klares Bild davon hat, wie das Ergebnis am Ende aussehen soll.
- ### Vor allem eine ausführliche Spezifikation, aus der man der KI die notwendigen Abschnitte übergeben kann hilft hier sehr. 
- ### Auch Code Schnipsel, aus ähnlichen Projekten, die man der KI übergibt damit diese sie als Referenz nutzen kann können das Ergebnis deutlich verbessern. 
- ### Besonders überascht hat mich hier wie gut die Statischen Pages aus dem Figma Make Prototyp von React nach Vue.js "übersetzt" wurden.