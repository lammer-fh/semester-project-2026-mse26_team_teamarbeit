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

      <StateMessage v-if="roomStore.isLoading">Zimmer werden geladen...</StateMessage>
      <StateMessage v-else-if="roomStore.error" :error="true">{{ roomStore.error }}</StateMessage>
      <StateMessage v-else-if="rooms.length === 0">Es wurden keine Zimmer gefunden.</StateMessage>

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
const { getIcon } = getIonicIcon();
import PageTemplate from '@/components/templates/PageTemplate.vue';
import PaginationControls from '@/components/molecules/PaginationControls.vue';
import RoomCard, { type RoomCardData } from '@/components/organisms/RoomCard.vue';
import { useRoomStore } from '@/stores/room.store';
import type { Room } from '@/models/room.model';
import {getIonicIcon} from "@/composables/getIonicIcon";
import StateMessage from "@/components/atoms/StateMessage.vue";

const roomStore = useRoomStore();

const currentPage = ref(1);
const roomsPerPage = 6;

const fallbackRoomImage = '/img/room-placeholder.jpg';

const totalPages = computed(() => roomStore.pagination?.totalPages ?? 0);

const rooms = computed<RoomCardData[]>(() =>
    (roomStore.rooms ?? []).map(mapRoomToCardData)
);

const mapRoomToCardData = (room: Room): RoomCardData => ({
  id: room.id,
  name: room.name,
  description: room.description,
  price: String(room.pricePerNight),
  image: room.coverImagePath ?? room.imagePaths?.[0] ?? fallbackRoomImage,
  images: room.imagePaths?.length > 0 ? room.imagePaths : [fallbackRoomImage],
  features: (room.roomExtras ?? []).map((extra) => ({
    label: extra.name,
    icon: getIcon(extra.icon, extra.name),
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