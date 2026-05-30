<template>
  <article class="card card-hover room-card">
    <div class="room-image-wrapper">
      <img :src="room.image" :alt="room.name" class="room-image"/>
    </div>

    <div class="room-content">
      <h2>{{ room.name }}</h2>
      <p>{{ room.description }}</p>

      <ul class="room-features">
        <IconPill
            v-for="feature in room.features"
            :key="feature.label"
            :label="feature.label"
            :icon="feature.icon"
        />
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
</template>

<script setup lang="ts">
import { IonButton } from '@ionic/vue';
import IconPill from '@/components/atoms/IconPill.vue';

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

.room-features {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin: 0;
  padding: 0 0 1.5rem;
  border-bottom: 1px solid #e7e5e4;
  list-style: none;
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