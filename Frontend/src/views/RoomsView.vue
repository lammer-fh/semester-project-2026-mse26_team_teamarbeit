<template>
  <PageTemplate>
    <div class="rooms-container">
      <header>
        <h1>Unsere Zimmer</h1>
        <p>
          Entdecken Sie unsere sorgfältig gestalteten Zimmer und Suiten. Jedes Zimmer
          bietet modernen Komfort und individuelle Annehmlichkeiten für Ihren perfekten Aufenthalt.
        </p>
      </header>

      <section class="rooms-grid" aria-label="Zimmerübersicht">
        <article v-for="room in currentRooms" :key="room.id" class="card card-hover">
          <div class="room-image-wrapper">
            <img :src="room.image" :alt="room.name" class="room-image"/>
          </div>

          <div class="room-content">
            <h2>{{ room.name }}</h2>
            <p>{{ room.description }}</p>

            <ul class="room-features">
              <li v-for="feature in room.features" :key="feature.label">
                <IonIcon :icon="feature.icon" aria-hidden="true"/>
                <span>{{ feature.label }}</span>
              </li>
            </ul>

            <div class="room-footer">
              <p class="room-price">
                <span class="room-price-amount">€{{ room.price }}</span>
                <span class="room-price-period">/ Nacht</span>
              </p>

              <div class="room-actions">
                <ion-button size="default" class="white-button">
                  Details ansehen
                </ion-button>

                <ion-button size="default" class="orange-button">
                  Verfügbarkeit prüfen
                </ion-button>
              </div>
            </div>
          </div>
        </article>
      </section>

      <nav v-if="totalPages > 1" class="pagination" aria-label="Seitennavigation">
        <ion-button size="default" class="white-button"
                    :disabled="currentPage === 1"
                    aria-label="Vorherige Seite"
                    @click="goToPage(currentPage - 1)"
        >
          <IonIcon slot="icon-only" :icon="chevronBack"></IonIcon>
        </ion-button>

        <ion-button size="default" class="white-button"
                    v-for="page in totalPages"
                    :key="page"
                    :class="{ active: currentPage === page }"
                    @click="goToPage(page)"
        >
          {{ page }}
        </ion-button>

        <ion-button size="default" class="white-button"
                    :disabled="currentPage === totalPages"
                    aria-label="Nächste Seite"
                    @click="goToPage(currentPage + 1)"
        >
          <IonIcon slot="icon-only" :icon="chevronForward"></IonIcon>
        </ion-button>
      </nav>
    </div>
  </PageTemplate>
</template>

<script setup lang="ts">
import {computed, ref} from 'vue';
import {IonButton, IonIcon} from '@ionic/vue';
import {
  bedOutline,
  briefcaseOutline,
  cafeOutline,
  happyOutline,
  homeOutline,
  peopleOutline,
  personOutline,
  restaurantOutline,
  sparklesOutline,
  tvOutline,
  waterOutline,
  wifiOutline,
  chevronBack,
  chevronForward,
} from 'ionicons/icons';
import PageTemplate from "@/components/templates/PageTemplate.vue";

interface RoomFeature {
  label: string;
  icon: string;
}

interface Room {
  id: number;
  name: string;
  description: string;
  price: string;
  image: string;
  features: RoomFeature[];
}

const rooms: Room[] = [
  {
    id: 1,
    name: 'Classic Einzelzimmer',
    description: 'Gemütliches Zimmer mit moderner Ausstattung und ruhiger Atmosphäre.',
    price: '89',
    image: 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?auto=format&fit=crop&w=900&q=80',
    features: [
      {label: '1 Person', icon: personOutline},
      {label: 'Kostenloses WLAN', icon: wifiOutline},
      {label: 'Schreibtisch', icon: briefcaseOutline},
    ],
  },
  {
    id: 2,
    name: 'Comfort Doppelzimmer',
    description: 'Helles Doppelzimmer mit komfortablem Bett und stilvollem Ambiente.',
    price: '129',
    image: 'https://images.unsplash.com/photo-1611892440504-42a792e24d32?auto=format&fit=crop&w=900&q=80',
    features: [
      {label: '2 Personen', icon: peopleOutline},
      {label: 'Queen-Size-Bett', icon: bedOutline},
      {label: 'Bad mit Dusche', icon: waterOutline},
    ],
  },
  {
    id: 3,
    name: 'Deluxe Zimmer',
    description: 'Großzügiges Zimmer mit hochwertigen Materialien und zusätzlichem Komfort.',
    price: '159',
    image: 'https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=900&q=80',
    features: [
      {label: '2 Personen', icon: peopleOutline},
      {label: 'Sitzecke', icon: homeOutline},
      {label: 'Minibar', icon: cafeOutline},
    ],
  },
  {
    id: 4,
    name: 'Junior Suite',
    description: 'Elegante Suite mit separatem Wohnbereich für einen entspannten Aufenthalt.',
    price: '219',
    image: 'https://images.unsplash.com/photo-1591088398332-8a7791972843?auto=format&fit=crop&w=900&q=80',
    features: [
      {label: '2 Personen', icon: peopleOutline},
      {label: 'Wohnbereich', icon: tvOutline},
      {label: 'Premium-Ausstattung', icon: sparklesOutline},
    ],
  },
  {
    id: 5,
    name: 'Familienzimmer',
    description: 'Geräumiges Zimmer für Familien mit praktischer Ausstattung und viel Platz.',
    price: '249',
    image: 'https://images.unsplash.com/photo-1566665797739-1674de7a421a?auto=format&fit=crop&w=900&q=80',
    features: [
      {label: '4 Personen', icon: peopleOutline},
      {label: 'Doppelbett & Schlafsofa', icon: bedOutline},
      {label: 'Familienfreundlich', icon: happyOutline},
    ],
  },
  {
    id: 6,
    name: 'Superior Suite',
    description: 'Luxuriöse Suite mit besonderem Komfort und exklusiver Einrichtung.',
    price: '299',
    image: 'https://images.unsplash.com/photo-1578683010236-d716f9a3f461?auto=format&fit=crop&w=900&q=80',
    features: [
      {label: '2 Personen', icon: peopleOutline},
      {label: 'King-Size-Bett', icon: bedOutline},
      {label: 'Luxusbad', icon: restaurantOutline},
    ],
  },
];

const currentPage = ref(1);
const roomsPerPage = 5;

const totalPages = computed(() => Math.ceil(rooms.length / roomsPerPage));

const currentRooms = computed(() => {
  const startIndex = (currentPage.value - 1) * roomsPerPage;
  const endIndex = startIndex + roomsPerPage;

  return rooms.slice(startIndex, endIndex);
});

const goToPage = (page: number): void => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
    window.scrollTo({top: 0, behavior: 'smooth'});
  }
};
</script>

<style scoped>
.rooms-container {
  width: 100%;
  max-width: 96rem;
  margin: 0 auto;
  padding: 16px;
}

.rooms-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 2rem;
  margin-bottom: 3rem;
}

.room-image-wrapper {
  position: relative;
  height: 14rem;
  overflow: hidden;
  background: #f5f5f4;
}

.room-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 220ms ease;
}

.card:hover .room-image {
  transform: scale(1.04);
}

.room-content {
  display: flex;
  flex: 1;
  flex-direction: column;
  padding: 1.5rem;
}

.room-content h2 {
  margin: 0 0 0.75rem;
  color: #1c1917;
  font-size: 1.25rem;
  line-height: 1.75rem;
  font-weight: 700;
}

.room-content p {
  margin: 0 0 1rem;
  color: #57534e;
  line-height: 1.625;
}

.room-features {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin: 0;
  padding: 0 0 1.5rem;
  border-bottom: 1px solid #e7e5e4;
  list-style: none;
}

.room-features li {
  display: inline-flex;
  align-items: center;
  gap: 0.35rem;
  padding: 0.4rem 0.7rem;
  border-radius: 999px;
  background: #f5f5f4;
  color: #44403c;
  font-size: 0.8125rem;
  font-weight: 500;
}

.room-features ion-icon {
  color: var(--app-orange);
  font-size: 1rem;
  flex-shrink: 0;
}

.room-footer {
  margin-top: auto;
  padding-top: 1.25rem;
}

.room-price {
  display: flex;
  align-items: baseline;
  gap: 0.35rem;
  margin: 0 0 1rem;
}

.room-price-amount {
  color: var(--app-orange);
  font-size: 2rem;
  line-height: 1;
  font-weight: 700;
}

.room-price-period {
  color: #78716c;
  font-size: 0.875rem;
  font-weight: 500;
}

.room-actions {
  display: grid;
  grid-template-columns: 1fr;
  gap: 0.75rem;
}

.room-actions ion-button {
  width: 100%;
  margin: 0;
}

.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

@media (min-width: 640px) {
  .room-actions {
    grid-template-columns: 1fr 1fr;
  }
}

@media (min-width: 768px) {
  .rooms-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .rooms-header h1 {
    font-size: 2.5rem;
    line-height: 1.15;
  }
}

@media (min-width: 1024px) {
  .rooms-grid {
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }

  .room-image-wrapper {
    height: 15rem;
  }

  .room-content {
    padding: 2rem;
  }
}
</style>