<template>
  <PaddedPageTemplate>
    <BackButton to="/rooms">Zurück zur Übersicht</BackButton>

    <StateMessage v-if="roomStore.isLoading">Zimmer wird geladen...</StateMessage>
    <StateMessage v-else-if="roomStore.error" :error="true">{{ roomStore.error }}</StateMessage>

    <template v-else-if="room">
      <RoomImageCarousel :images="room.images" :alt="room.name" />

      <div class="room-details">
        <div class="room-header">
          <h1>{{ room.name }}</h1>
          <PriceDisplay :price="room.price" />
        </div>

        <div class="room-section">
          <h2>Beschreibung</h2>
          <p>{{ room.description }}</p>
        </div>

        <div class="room-section">
          <h2>Ausstattung</h2>
          <RoomFeatureList :features="room.features" />
        </div>

        <div class="room-actions">
          <ion-button expand="block">
            Verfügbarkeit prüfen
          </ion-button>
          <ion-button expand="block" fill="outline" router-link="/rooms">
            Zurück zur Übersicht
          </ion-button>
        </div>
      </div>
    </template>

    <template v-else>
      <StateMessage>Zimmer nicht gefunden.</StateMessage>
      <ion-button router-link="/rooms">Zurück zur Übersicht</ion-button>
    </template>
  </PaddedPageTemplate>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { IonButton } from '@ionic/vue';
import PaddedPageTemplate from '@/components/templates/PaddedPageTemplate.vue';
import BackButton from '@/components/molecules/BackButton.vue';
import StateMessage from '@/components/atoms/StateMessage.vue';
import RoomImageCarousel from '@/components/molecules/RoomImageCarousel.vue';
import PriceDisplay from '@/components/atoms/PriceDisplay.vue';
import RoomFeatureList from '@/components/molecules/RoomFeatureList.vue';
import { useRoomStore } from '@/stores/room.store';
import { getIonicIcon } from '@/composables/getIonicIcon';
import type { RoomCardData } from '@/components/organisms/RoomCard.vue';

const { getIcon } = getIonicIcon();
const route = useRoute();
const roomStore = useRoomStore();

const fallbackRoomImage = '/img/room-placeholder.jpg';

const room = computed(() => {
  const id = route.params.id as string;
  const found = roomStore.rooms?.find((r) => r.id === id);
  if (!found) return undefined;

  return {
    id: found.id,
    name: found.name,
    description: found.description,
    price: String(found.pricePerNight),
    image: found.coverImagePath ?? fallbackRoomImage,
    images: found.imagePaths && found.imagePaths.length > 0 ? found.imagePaths : [fallbackRoomImage],
    features: (found.roomExtras ?? []).map((extra) => ({
      label: extra.name,
      icon: getIcon(extra.icon, extra.name),
    })),
  };
});

onMounted(async () => {
  if (!roomStore.rooms?.length) {
    await roomStore.fetchRooms({ page: 0, size: 100 });
  }
});
</script>

<style scoped>
.room-details {
  background: #ffffff;
  border-radius: 0.75rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
  padding: 2rem;
}
.room-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
  gap: 1rem;
  margin-bottom: 1.5rem;
}
.room-header h1 {
  font-size: 1.75rem;
  font-weight: 700;
  color: #1c1917;
  margin: 0;
}
.room-section {
  margin-bottom: 2rem;
}
.room-section h2 {
  font-size: 1.15rem;
  font-weight: 600;
  color: #1c1917;
  margin-bottom: 1rem;
}
.room-section p {
  color: #44403c;
  line-height: 1.7;
}
.room-actions {
  display: grid;
  grid-template-columns: 1fr;
  gap: 0.75rem;
}
@media (min-width: 640px) {
  .room-actions {
    grid-template-columns: 1fr 1fr;
  }
}
</style>