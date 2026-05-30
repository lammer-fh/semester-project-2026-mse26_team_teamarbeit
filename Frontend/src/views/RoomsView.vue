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
        <RoomCard
            v-for="room in currentRooms"
            :key="room.id"
            :room="room"
        />
      </section>

      <PaginationControls
          :current-page="currentPage"
          :total-pages="totalPages"
          @page-change="goToPage"
      />
    </div>
  </PageTemplate>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
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
} from 'ionicons/icons';
import PageTemplate from '@/components/templates/PageTemplate.vue';
import PaginationControls from '@/components/molecules/PaginationControls.vue';
import RoomCard, { type RoomCardData } from '@/components/organisms/RoomCard.vue';

const rooms: RoomCardData[] = [
  {
    id: 1,
    name: 'Classic Einzelzimmer',
    description: 'Gemütliches Zimmer mit moderner Ausstattung und ruhiger Atmosphäre.',
    price: '89',
    image: 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?auto=format&fit=crop&w=900&q=80',
    features: [
      { label: '1 Person', icon: personOutline },
      { label: 'Kostenloses WLAN', icon: wifiOutline },
      { label: 'Schreibtisch', icon: briefcaseOutline },
    ],
  },
  {
    id: 2,
    name: 'Comfort Doppelzimmer',
    description: 'Helles Doppelzimmer mit komfortablem Bett und stilvollem Ambiente.',
    price: '129',
    image: 'https://images.unsplash.com/photo-1611892440504-42a792e24d32?auto=format&fit=crop&w=900&q=80',
    features: [
      { label: '2 Personen', icon: peopleOutline },
      { label: 'Queen-Size-Bett', icon: bedOutline },
      { label: 'Bad mit Dusche', icon: waterOutline },
    ],
  },
  {
    id: 3,
    name: 'Deluxe Zimmer',
    description: 'Großzügiges Zimmer mit hochwertigen Materialien und zusätzlichem Komfort.',
    price: '159',
    image: 'https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=900&q=80',
    features: [
      { label: '2 Personen', icon: peopleOutline },
      { label: 'Sitzecke', icon: homeOutline },
      { label: 'Minibar', icon: cafeOutline },
    ],
  },
  {
    id: 4,
    name: 'Junior Suite',
    description: 'Elegante Suite mit separatem Wohnbereich für einen entspannten Aufenthalt.',
    price: '219',
    image: 'https://images.unsplash.com/photo-1591088398332-8a7791972843?auto=format&fit=crop&w=900&q=80',
    features: [
      { label: '2 Personen', icon: peopleOutline },
      { label: 'Wohnbereich', icon: tvOutline },
      { label: 'Premium-Ausstattung', icon: sparklesOutline },
    ],
  },
  {
    id: 5,
    name: 'Familienzimmer',
    description: 'Geräumiges Zimmer für Familien mit praktischer Ausstattung und viel Platz.',
    price: '249',
    image: 'https://images.unsplash.com/photo-1566665797739-1674de7a421a?auto=format&fit=crop&w=900&q=80',
    features: [
      { label: '4 Personen', icon: peopleOutline },
      { label: 'Doppelbett & Schlafsofa', icon: bedOutline },
      { label: 'Familienfreundlich', icon: happyOutline },
    ],
  },
  {
    id: 6,
    name: 'Superior Suite',
    description: 'Luxuriöse Suite mit besonderem Komfort und exklusiver Einrichtung.',
    price: '299',
    image: 'https://images.unsplash.com/photo-1578683010236-d716f9a3f461?auto=format&fit=crop&w=900&q=80',
    features: [
      { label: '2 Personen', icon: peopleOutline },
      { label: 'King-Size-Bett', icon: bedOutline },
      { label: 'Luxusbad', icon: restaurantOutline },
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
    window.scrollTo({ top: 0, behavior: 'smooth' });
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
}
</style>