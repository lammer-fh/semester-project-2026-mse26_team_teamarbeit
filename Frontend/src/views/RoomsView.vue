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

      <p v-if="roomStore.isLoading" class="rooms-state">
        Zimmer werden geladen...
      </p>

      <p v-else-if="roomStore.error" class="rooms-state rooms-state-error">
        {{ roomStore.error }}
      </p>

      <p v-else-if="rooms.length === 0" class="rooms-state">
        Es wurden keine Zimmer gefunden.
      </p>

      <section v-else class="rooms-grid" aria-label="Zimmerübersicht">
        <RoomCard
            v-for="room in rooms"
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
import { computed, onMounted, ref } from 'vue';
import {
  bedOutline,
  briefcaseOutline,
  cafeOutline,
  happyOutline,
  homeOutline,
  peopleOutline,
  restaurantOutline,
  sparklesOutline,
  tvOutline,
  waterOutline,
  wifiOutline,
} from 'ionicons/icons';
import PageTemplate from '@/components/templates/PageTemplate.vue';
import PaginationControls from '@/components/molecules/PaginationControls.vue';
import RoomCard, { type RoomCardData } from '@/components/organisms/RoomCard.vue';
import { useRoomStore } from '@/stores/room.store';
import type { Room } from '@/models/room.model';

const roomStore = useRoomStore();

const currentPage = ref(1);
const roomsPerPage = 5;

// TODO: Replace with self-hosted
const fallbackRoomImage = 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?auto=format&fit=crop&w=900&q=80';

const totalPages = computed(() => roomStore.pagination?.totalPages ?? 0);

const rooms = computed<RoomCardData[]>(() =>
    (roomStore.rooms ?? []).map(mapRoomToCardData)
);

const getFeatureIcon = (feature: string): string => {
  const normalizedFeature = feature.toLowerCase();

  if (normalizedFeature.includes('wlan') || normalizedFeature.includes('wifi')) {
    return wifiOutline;
  }

  if (normalizedFeature.includes('person') || normalizedFeature.includes('gäste') || normalizedFeature.includes('gast')) {
    return peopleOutline;
  }

  if (normalizedFeature.includes('bett') || normalizedFeature.includes('bed')) {
    return bedOutline;
  }

  if (normalizedFeature.includes('bad') || normalizedFeature.includes('dusche')) {
    return waterOutline;
  }

  if (normalizedFeature.includes('schreibtisch') || normalizedFeature.includes('arbeitsplatz')) {
    return briefcaseOutline;
  }

  if (normalizedFeature.includes('minibar') || normalizedFeature.includes('kaffee')) {
    return cafeOutline;
  }

  if (normalizedFeature.includes('wohn') || normalizedFeature.includes('sitzecke')) {
    return homeOutline;
  }

  if (normalizedFeature.includes('tv') || normalizedFeature.includes('fernseher')) {
    return tvOutline;
  }

  if (normalizedFeature.includes('famil')) {
    return happyOutline;
  }

  if (normalizedFeature.includes('premium') || normalizedFeature.includes('luxus')) {
    return sparklesOutline;
  }

  return restaurantOutline;
};

const mapRoomToCardData = (room: Room): RoomCardData => ({
  id: room.id,
  name: room.name,
  description: room.description,
  price: String(room.pricePerNight),
  image: room.coverImagePath ?? room.imagePaths?.[0] ?? fallbackRoomImage,
  features: (room.roomExtras ?? []).map((extra) => ({
    label: extra.name,
    icon: extra.icon ?? getFeatureIcon(extra.name),
  })),
});

const fetchRooms = async (): Promise<void> => {
  await roomStore.fetchRooms({
    page: currentPage.value - 1,
    size: roomsPerPage,
  });
};

const goToPage = async (page: number): Promise<void> => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
    await fetchRooms();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
};

onMounted(() => {
  void fetchRooms();
});
</script>

<style scoped>
.rooms-container {
  width: 100%;
  max-width: 96rem;
  margin: 0 auto;
  padding: 16px;
}

.rooms-state {
  margin: 2rem 0 3rem;
  color: #57534e;
  text-align: center;
  font-size: 1rem;
}

.rooms-state-error {
  color: #b91c1c;
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