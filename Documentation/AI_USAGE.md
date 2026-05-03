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
### Die Screenshots vom Figma-Prototyp befinden sich in Paperprototype.md