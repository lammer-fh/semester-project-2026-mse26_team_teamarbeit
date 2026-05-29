# AI Usage:

## 5. Vue 3 Frontend-Architektur, Axios-Client und Room-Feature-Slice

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