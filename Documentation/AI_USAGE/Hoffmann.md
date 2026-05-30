# AI Usage:

## 1. Vue 3 Frontend-Architektur, Axios-Client und Room-Feature-Slice

### KI Tool

- Claude Sonnet (claude.ai)

### Ziel

Vor dem eigentlichen Implementierungsstart sollte gemeinsam mit der KI eine saubere, skalierbare Frontend-Architektur für die Vue 3 / Ionic-App erarbeitet werden. Ziel war es, Best Practices für Ordnerstruktur, API-Schicht, State Management (Pinia), Komponentendesign und TypeScript-Typisierung zu klären – und anschließend den vollständigen Feature-Slice für die `Room`-Entität als konkretes, wiederverwendbares Implementierungsbeispiel umzusetzen.

### Beschreibung / Prompt

Der Einstieg erfolgte mit einer Architektur-Frage zur Ordnerstruktur:

```text
best practice vue3 folder structure for an app using a pinia store,
axios api & atomic component design with ionic framework
```

Daraufhin wurden gezielt Einzelentscheidungen geklärt:

```text
i intended to call the api/index.ts file something like "axiosClient.ts"
but i really want to be strict with best practices this time.
which one would be better? same for router
```

```text
when building an app with like 3 entities (guest, room, booking) is it
clean architecture to bundle all endpoints in one file or separate them
by entity? i will have a model for each but how about separation in
store, endpoints?
```

```text
when being used to options api, how to structure a complete & clean
example vue component?
```

```text
i want to finish up my axios client + one store / model / endpoints
workflow. thats our api spec in appendage, do the basic room example
please in cleanest way possible
```

Nach der initialen Implementierung wurde ein TypeScript-Fehler aus der IDE gemeldet und gemeinsam analysiert:

```text
fetchRoomById it says this on intellisense on the data variable on this
line `currentRoom.value = data` — [TS error message]
```

### Analyse durch die KI

Die KI hat die Architekturentscheidungen systematisch begründet:

1. `api/index.ts` ist gegenüber `axiosClient.ts` vorzuziehen, da der Dateiname die Rolle als Einstiegspunkt der API-Schicht beschreibt und nicht die verwendete Bibliothek preisgibt. Bei einem späteren Austausch von Axios wäre der Name sonst irreführend.

2. Endpoints, Stores und Models sollen pro Entität in eigene Dateien aufgeteilt werden. Jede Entität bildet einen vertikalen Slice: ein Model, eine Endpoint-Datei, ein Store. Stores dürfen dabei nur ihre eigene Endpoint-Datei importieren und nie direkt auf die API-Module einer anderen Entität zugreifen.

3. Die Composition API wurde gegenüber der Options API klar bevorzugt, da sie bessere TypeScript-Integration, composable-basierte Wiederverwendbarkeit und konsistente Syntax mit Pinia-Setup-Stores bietet.

### Ergebnis / Umgesetzte Änderungen

#### Ordnerstruktur

Es wurde eine vollständige Projektstruktur nach Atomic Design und Clean Architecture erarbeitet:

```text
src/
├── api/
│   ├── index.ts                        # Axios-Instanz, Interceptors
│   └── endpoints/
│       ├── room.endpoints.ts
│       ├── booking.endpoints.ts
│       └── guest.endpoints.ts
├── components/
│   ├── atoms/
│   ├── molecules/
│   ├── organisms/
│   └── templates/
├── composables/
├── models/
│   ├── shared.model.ts
│   ├── room.model.ts
│   ├── booking.model.ts
│   └── guest.model.ts
├── stores/
│   ├── room.store.ts
│   ├── booking.store.ts
│   └── guest.store.ts
├── views/
├── router/
├── utils/
├── constants/
└── theme/
```

#### Axios-Client (`api/index.ts`)

- Zentrale Axios-Instanz mit `baseURL` aus `.env`
- Response-Interceptor, der `error.response.data.errorMessage` in einen nativen `Error` umwandelt, sodass alle `catch`-Blöcke im Store ein einheitliches Fehlerformat erhalten

#### Models (`models/shared.model.ts`, `models/room.model.ts`)

Folgende Typen wurden definiert:

- `PageMeta` und `PaginationParams` als gemeinsam genutzte Typen in `shared.model.ts`
- `Room` als Kern-Entity
- `RoomListParams` mit Pflichtfeld `page` entsprechend der API-Spezifikation
- `RoomAvailabilityParams` für den Verfügbarkeits-Endpunkt
- `RoomListResponse` und `RoomAvailabilityResponse` als typisierte API-Responses

#### Endpoints (`api/endpoints/room.endpoints.ts`)

Alle vier Room-Endpunkte der API-Spezifikation wurden umgesetzt:

```text
GET  /rooms                      → RoomApi.getList(params)
GET  /rooms/:id                  → RoomApi.getById(id)
GET  /rooms/:id/availability     → RoomApi.checkAvailability(id, params)
GET  /rooms/:id/bookings         → RoomApi.getBookings(id, params)
```

Jede Methode gibt nach `.then(r => r.data)` direkt den typisierten Payload zurück. Die Endpoint-Schicht ist damit zuständig für URL-Mapping und Response-Unwrapping – keine weiteren Verantwortlichkeiten.

#### Store (`stores/room.store.ts`)

- State: `rooms`, `currentRoom`, `roomBookings`, `pagination`, `isAvailable`, `isLoading`, `error`
- Computed: `hasNextPage` auf Basis der `PageMeta`
- `withLoading`-Helper-Funktion, die `isLoading` und `error` zentral verwaltet und in allen Aktionen wiederverwendet wird, um Boilerplate zu vermeiden
- Aktionen: `fetchRooms`, `fetchRoomById`, `checkAvailability`, `fetchRoomBookings`, `clearCurrentRoom`
- Stores importieren ausschließlich ihre eigene Endpoint-Datei

### Wichtige Architekturentscheidungen

- **Keine Entität in der Endpoint-Schicht einer anderen:** Stores dürfen cross-entity State über `useXStore()` lesen, aber niemals die Endpoint-Datei einer fremden Entität importieren.
- **Atomic Design für Komponenten:** Atome und Moleküle enthalten keine Store-Logik. Store-Zugriff ist Organismen oder Views vorbehalten.
- **`withLoading`-Pattern:** Einheitliches Fehler- und Ladezustandshandling über einen gemeinsamen Helper im Store, statt wiederholtem `try/catch`-Boilerplate in jeder Aktion.
- **Composition API als einzig verwendeter Komponentenstil im gesamten Projekt für konsistente Syntax über Komponenten, Composables und Stores hinweg.

## Session — 2026-05-23 (Friday)

**Model:** GPT-5.5 (OpenAI Codex)  
**Interface:** ChatGPT Codex agent (VS Code sandbox)  
**Project context:** Vue 3 / Ionic hotel booking app — User Story 3: room availability check
 
---

### Task (US3 Brief)

> *"As a guest I want to check whether a specific room is available for my desired travel period in order to determine whether I will be able to book the room.*
>
> *An API endpoint is available to retrieve room availability. Details can be found in the API documentation. The user should be able to define their booking period within the interface. You have full creative freedom in deciding how best to present this to the user. Think innovatively and outside the box.*
>
> *[…] i want our frontend to be done in clean architecture vue3 with typescript. i dont care about options or composition api. do not build the other features necessary as this story is ONLY about the availability check"*

**Goal:** Implement the full availability check feature end-to-end: domain types, API integration, Pinia store wiring, and the `AvailabilityCheck.vue` page with three period-entry variants.

**Description:** Codex was handed the user story, the API specification, and a Figma-make React export as visual reference. It was given autonomy over component design and architecture within the Vue 3 / Ionic / TypeScript constraint.

**Result:**
- Added availability domain types and date/validation use cases in `availability.usecase.ts`
- Wired `GET /rooms/:id/availability` through `room.endpoints.ts` and `room.store.ts`
- Built `AvailabilityCheck.vue` with three period-entry variants: exact dates, smart presets, arrival + nights
- Added `/availability` and `/availability/:id` routes; incorrectly set `/` to redirect to availability screen
- Fixed Pinia setup in `main.ts`
- 8 files changed, +920 / -123 lines
- **Known issues flagged by model:** `npm run build` could not be verified due to broken `vue-tsc` shim; backend did not yet expose the `/rooms/{id}/availability` endpoint
---

### Prompt 1

> *"there should be no room selection in availability, the site needs a room id url param to be accessed. it also is not the default page in the app, that should be home. the retrieval of room data for name, picture & price should come from the room store. the 3 date modes are fine but the css for the selection is still a bit scuffed.*
>
> *i also dont know about the room store & endpoint changes. you have a very clear specification under API_specification.md and should NOT change the contract at all. availability is a boolean fuck your response object. also stop overengineering things for no reason"*

**Goal:** Fix three regressions introduced in the initial implementation: wrong default route, room selection UI that shouldn't exist, and a broken API contract.

**Description:** Codex had added a room-selection dropdown to the availability page, redirected `/` to availability instead of home, introduced a custom availability response object instead of the specified `{ available: boolean }`, and made unsanctioned changes to the room store and endpoints.

**Result:**
- `/` redirects to `/home` again
- Availability only accessible at `/availability/:id` — no room selection UI
- Room name, image, description, and price loaded via `roomStore.fetchRoomById(roomId)` / `roomStore.currentRoom`
- Store state reverted to `isAvailable: boolean | null`
- API endpoint follows `API_specification.md`: `GET /rooms/:id/availability` returns `{ available: boolean }`
- Custom availability response object and room-list normalization removed
- Date-mode selector CSS reworked into a cleaner custom segmented control
- 6 files changed
---

### Prompt 2

> *"more defensive access in case a faulty room id is being passed, nothing should break. ui should be vertical: room info above the availability check. change the h1 back to what it was before and fuck that eyebrow shit. also give me back my header todo as this is being done later by someone else"*

**Goal:** Harden the page against bad route params, fix the layout direction, remove unwanted decorative copy, and restore the header placeholder comment.

**Description:** The page was crashing on invalid room IDs, the layout was horizontal, Codex had added an "eyebrow" subtitle above the H1, and the `TODO: insert header component here` comment had been replaced with an actual header implementation that wasn't part of this story.

**Result:**
- `TODO: insert header component here` restored in `ion-header`
- Eyebrow text and styling removed
- H1 restored to previous value
- Layout changed to vertical: room info panel first, availability form below
- Defensive guards added: no crash on bad room ID, "Zimmer nicht gefunden" fallback state shown, submit button disabled if `currentRoom` is null, image and price rendering guarded
- Stale availability cleared before each new check
- 1 file changed, +41 / -25
---

### Prompt 3

> *"the title should just be 'Verfügbarkeit prüfen'"*

**Goal:** Simplify the H1 copy to match the German-language tone of the rest of the app.

**Result:**
- H1 changed to `Verfügbarkeit prüfen`
- 1 line changed
---

### Prompt 4

> *"the room info is HUGE, make it way smaller so the biggest thing on this page is still the availability check. image should be like somewhere around 240x120 and the whole info block horizontally aside the picture"*

**Goal:** Reduce the visual weight of the room info panel so the availability form remains the primary focus.

**Description:** The room info block was rendering at full width and large scale, dominating the page. The requirement was a compact horizontal summary strip — thumbnail image alongside text — sitting above the main form.

**Result:**
- Room info panel resized to a compact horizontal layout: 240×120px image beside name, description, and price
- Smaller font sizes for the info block
- Availability check form remains the dominant element on the page
- 1 file changed, +21 / -5
---

### Prompt 5

> *"const api = axios.create({ baseURL: import.meta.env.VITE_API_BASE_URL, headers: { 'Content-Type': 'application/json' } }) my api endpoints are misconfigured since my vite server only serves the client which runs on http://localhost:5173, but my backend is actually located on http://localhost:8080"*

**Goal:** Fix the Axios base URL so API calls from the Vite dev server reach the Spring Boot backend running on a different port.

**Description:** `VITE_API_BASE_URL` was unset, causing Axios to call the Vite origin for API requests. A direct `http://localhost:8080` hardcode would have worked locally but broken in Docker.

**Result:**
- Axios base URL set to `import.meta.env.VITE_API_BASE_URL ?? '/api'`
- Vite dev server proxy added: `/api/*` → `http://backend:8080/*` (host configurable via `VITE_API_PROXY_TARGET`)
- Browser calls `http://localhost:5173/api/rooms/...`; Vite proxies internally to `http://backend:8080/rooms/...`
- 2 files changed: `src/api/index.ts`, `vite.config.ts`
---

### Prompt 6

> *"reformulate everything in availabilitycheck.vue to options api"*

**Goal:** Convert `AvailabilityCheck.vue` from `<script setup>` / Composition API to the Vue Options API to match the project's chosen style (consistent with the rest of the codebase).

**Result:**
- Rewrote script block using `defineComponent`, `data()`, `computed`, `methods`, `mounted`
- Template and styles left unchanged
- No Composition API refs or setup usage remaining
- 1 file changed, +118 / -92
---

### Prompt 7

> *"i have the project running in 2 containers, one backend under localhost 8080 and a frontend vite server under 5173. when calling http://localhost:8080/rooms/87643f57-68f1-45c5-907c-0e8cd1c021c8 it works fine in the browser. when calling it from the localhost 5173 app i get: Cross-Origin Request Blocked: The Same Origin Policy disallows reading the remote resource at http://localhost:8080/rooms/... (Reason: CORS header 'Access-Control-Allow-Origin' missing). Status code: 200."*

**Goal:** Resolve the CORS error caused by the browser blocking cross-origin requests from the Vite frontend to the Spring Boot backend.

**Description:** Even though the Vite proxy had been configured in the previous step, it wasn't yet active because the dev server hadn't been restarted inside the container. The CORS error confirmed the browser was still calling `http://localhost:8080` directly rather than going through the `/api` proxy. Codex confirmed the fix was already in place from Prompt 5 and that restarting the Vite container would apply it.

**Result:**
- Confirmed proxy config from Prompt 5 was the correct solution
- Restart instruction given: restarting the Vite dev server/container applies the proxy and eliminates the cross-origin request
- No additional code changes required
- Session ended due to Codex rate limit (resets 2026-06-05)


# AI Usage Log

## Session — 2026-05-30

**Model:** Claude Sonnet 4.6 (Anthropic)  
**Interface:** claude.ai web chat  
**Project context:** Vue 3 / Ionic hotel booking app (Boutique Hotel Technikum)

---

### Prompt 1

> *"add a router link to the route "availabilityCheck" and pass the room id to it as a parameter"*

**Goal:** Wire up the "Verfügbarkeit prüfen" button in `RoomDetailsView` to navigate to the availability check page, passing the room ID as a route param.

**Description:** The button used a `@click="checkAvailability"` handler with an empty function body. The router already had an `/availability/:id` route named `AvailabilityCheck`. The task was to replace the imperative click handler with a declarative `router-link` binding.

**Result:**
- Replaced `@click="checkAvailability"` with `:router-link="` `` `/availability/${room.id}` ``"` on the `ion-button`
- Also converted the "Details ansehen" `@click` to `:router-link` for consistency
- Removed the now-dead `checkAvailability()` function from the script
- Explained why the string path was preferred over the named-route object syntax for a single-param route

---

### Prompt 2

> *"i will paste the finished pages of my app and you will have to adapt my last page in progress (checkAvailability) accordingly so it has the same styling on buttons and uses the same header and page template. dont change anything with the logic"*

**Goal:** Bring `AvailabilityCheck.vue` in line with the rest of the app's visual structure — same page wrapper, same back navigation, same header pattern — without touching any business logic.

**Description:** The page was using raw Ionic primitives (`ion-page`, `ion-header`, `ion-content`) with a custom shell. Other finished pages used `PaddedPageTemplate` and `BackButton`. The submit button also had custom CSS overriding the amber colour that `variables.css` already provides globally via `--ion-color-primary`.

**Result:**
- Replaced `ion-page` / `ion-header` / `ion-content` with `PaddedPageTemplate`
- Added `BackButton` linking back to `/rooms/:id`
- Moved the `<h1>` into a `<header>` element so `general.css` centering styles apply automatically, matching `RoomsView`
- Removed the redundant `ion-button[type='submit']` custom CSS block — `--ion-color-primary: #D97706` in `variables.css` handles it
- Removed unused Ionic component imports (`IonPage`, `IonHeader`, `IonContent`)

---

### Prompt 3

> *"looks great, now make the upper segment aka "roomInfoPanel" look like this room card component in terms of colors & extras list but keep the sizing, or just adapt the roomCard to have a smaller display option for this page without buttons and small just like roomInfoPanel"*

**Goal:** Make `RoomInfoPanel` match `RoomCard`'s visual language (colour palette, extras list, price display) while keeping its existing compact side-by-side layout.

**Description:** `RoomInfoPanel` used a custom amber/brown colour scheme and rendered the price as a raw `<strong>` tag and extras as ad-hoc markup. `RoomCard` used `RoomFeatureList` and `PriceDisplay` atoms and the neutral `#1c1917` / `#57534e` palette from `general.css`. The decision was made to update `RoomInfoPanel` in place rather than adding a size variant to `RoomCard`, keeping each component's responsibility clear.

**Result:**
- Added `.card` class to `<aside>` so `general.css` border, shadow, and background apply for free
- Replaced raw price `<strong>` with `<PriceDisplay>` component
- Added `<RoomFeatureList>` with a `mappedFeatures` computed property that uses the same `getIonicIcon` composable as the rest of the app
- Updated all text colours to match `RoomCard`: `#1c1917` headings, `#57534e` body, `#a8a29e` fallback icons
- Added the same `scale(1.04)` image hover transition from `RoomCard`
- Restyled loading/error states to use the neutral palette instead of the old custom amber/brown

---

### Prompt 4

> *"great, now make the verfügbarkeit prüfen buttons on this page also route to availability check page"*

**Goal:** Make the "Verfügbarkeit prüfen" buttons inside `RoomCard` navigate to the availability check page for each room.

**Description:** The button used `@click` with an inline `$router.push`. The fix was a one-liner in `RoomCard.vue` — no changes needed in `RoomsView` since routing is fully encapsulated in the card component.

**Result:**
- Replaced `@click="$router.push(` `` `/rooms/${room.id}` ``"` on the details button with `:router-link`
- Replaced the `@click` on the availability button with `:router-link="` `` `/availability/${room.id}` ``"`
- Both buttons now use declarative `router-link` bindings, consistent with every other navigation in the app

---

### Prompt 5

> *"okay great now last thing just make this component use the same page template and some 'booking 404 - coming soon' placeholder thing"*

**Goal:** Give the empty `BookingVue.vue` component a proper page structure and a user-friendly placeholder indicating the booking feature is not yet available.

**Description:** The component was a blank shell. It needed the standard `PaddedPageTemplate` + `BackButton` wrapper and a visually clear placeholder that matches the app's design system without being alarming to users.

**Result:**
- Added `PaddedPageTemplate` and `BackButton` (linking back to `/rooms/:id`, reading the room ID from `$route.params`)
- Built a centered `.card` placeholder with a `constructOutline` Ionic icon (amber, 4rem)
- Copy: "Buchung kommt bald" heading with an explanatory subline and two action buttons
- Buttons: "Zurück zum Zimmer" (outline, back to the specific room) and "Alle Zimmer ansehen" (primary, back to listing)
- Action grid uses the same `1fr` / `1fr 1fr` responsive pattern as the rest of the app

---

### Prompt 6

> *"would be cool if you can build a footer component out of our clickdummy html to be used inside the vue3 app"*

**Goal:** Convert a static Tailwind-based HTML footer from the click dummy into a reusable Vue 3 SFC that fits the existing app architecture.

**Description:** The source HTML used Tailwind utility classes and plain `<a href>` anchors. The Vue component needed scoped CSS instead of Tailwind, `<router-link>` for internal navigation, semantic HTML improvements, and a dynamic copyright year.

**Result:**
- Created `AppFooter.vue` (suggested location: `components/organisms/`)
- Three-column responsive grid (1 col mobile → 3 col at 768px) matching the original layout
- Tailwind colour tokens translated to CSS: `stone-900 → #1c1917`, `stone-400 → #a8a29e`, `stone-800 → #292524`
- `<address>` element used for contact block (semantic HTML)
- `<router-link>` for internal links with `.router-link-active` hover style
- `href` anchors with `tel:` and `mailto:` for phone and email
- `currentYear` computed property for a self-updating copyright year
- Provided integration snippet showing how to drop it into `PageTemplate.vue` above `</ion-content>`

---

### Prompt 7

> *"document what we did in this session for my AI_USAGE.md file. mention which model you are, my prompts verbatim, the goals, description & results"*

**Goal:** Produce a structured markdown record of the session for the project's AI usage log.

**Result:** This file.