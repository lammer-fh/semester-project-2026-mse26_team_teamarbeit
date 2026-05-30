<template>
  <article class="card card-hover room-card">
    <div class="room-image-wrapper">
      <img :src="room.image" :alt="room.name" class="room-image"/>
    </div>

    <div class="room-content">
      <h2>{{ room.name }}</h2>
      <p>{{ room.description }}</p>

      <RoomFeatureList :features="room.features" />

      <div class="room-footer">
        <PriceDisplay :price="room.price" />

        <div class="room-actions">
          <ion-button size="default" fill="outline" @click="$router.push(`/rooms/${room.id}`)">
            Details ansehen
          </ion-button>

          <ion-button size="default">
            Verfügbarkeit prüfen
          </ion-button>
        </div>
      </div>
    </div>
  </article>
</template>

<script setup lang="ts">
import { IonButton } from '@ionic/vue';
import IconPill from '@/components/atoms/IconPill.vue';
import PriceDisplay from "@/components/atoms/PriceDisplay.vue";
import RoomFeatureList from "@/components/molecules/RoomFeatureList.vue";

export interface RoomFeature {
  label: string;
  icon: string;
}

export interface RoomCardData {
  id: number;
  name: string;
  description: string;
  price: string;
  image: string;
  images: string;
  features: RoomFeature[];
}

defineProps<{
  room: RoomCardData;
}>();
</script>

<style scoped>
.room-card {
  display: flex;
  flex-direction: column;
  overflow: hidden;
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

.room-footer {
  margin-top: auto;
  padding-top: 1.25rem;
}

.room-actions {
  display: grid;
  grid-template-columns: 1fr;
  gap: 0.75rem;
  padding-top: 1.25rem;
}

.room-actions ion-button {
  width: 100%;
  margin: 0;
}

@media (min-width: 640px) {
  .room-actions {
    grid-template-columns: 1fr 1fr;
  }
}

@media (min-width: 1024px) {
  .room-image-wrapper {
    height: 15rem;
  }

  .room-content {
    padding: 2rem;
  }
}
</style>