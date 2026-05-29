<template>
  <aside class="room-panel">
    <div v-if="room" class="room-preview">
      <img
          v-if="room.imagePaths?.[0]"
          :src="room.imagePaths[0]"
          :alt="room.name"
      >
      <div v-else class="image-fallback">
        <ion-icon :icon="bedOutline" aria-hidden="true" />
      </div>

      <div>
        <h2>{{ room.name }}</h2>
        <p>{{ room.description }}</p>
        <strong v-if="typeof room.pricePerNight === 'number'">
          {{ formatPrice(room.pricePerNight) }} / Nacht
        </strong>
      </div>
    </div>

    <div v-else-if="isLoading" class="room-loading">
      <ion-spinner name="crescent" />
      <span>Zimmer wird geladen</span>
    </div>

    <div v-else class="room-error">
      <ion-icon :icon="bedOutline" aria-hidden="true" />
      <h2>Zimmer nicht gefunden</h2>
      <p>Die Verfuegbarkeit kann nur fuer ein gueltiges Zimmer geprueft werden.</p>
    </div>
  </aside>
</template>

<script lang="ts">
import { defineComponent, type PropType } from 'vue'
import { IonIcon, IonSpinner } from '@ionic/vue'
import { bedOutline } from 'ionicons/icons'
import type { Room } from '@/models/room.model'

export default defineComponent({
  name: 'RoomInfoPanel',
  components: {
    IonIcon,
    IonSpinner,
  },
  props: {
    room: {
      type: Object as PropType<Room | null>,
      default: null,
    },
    isLoading: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      bedOutline,
    }
  },
  methods: {
    formatPrice(value: number): string {
      return new Intl.NumberFormat('de-AT', {
        style: 'currency',
        currency: 'EUR',
        maximumFractionDigits: 0,
      }).format(value)
    },
  },
})
</script>

<style scoped>
.room-panel {
  padding: 14px;
  border: 1px solid rgba(72, 62, 51, 0.14);
  border-radius: 8px;
  background: #fffdfa;
  box-shadow: 0 14px 34px rgba(52, 39, 25, 0.08);
}

.room-preview {
  display: grid;
  grid-template-columns: 240px 1fr;
  gap: 16px;
  align-items: center;
}

.room-preview img,
.image-fallback {
  width: 100%;
  max-width: 240px;
  height: 120px;
  border-radius: 8px;
  object-fit: cover;
}

.image-fallback,
.room-loading,
.room-error {
  display: grid;
  place-items: center;
  background: #eadfd2;
  color: #7c5733;
}

.image-fallback ion-icon {
  font-size: 2.2rem;
}

.room-loading {
  min-height: 260px;
  gap: 12px;
  border-radius: 8px;
  font-weight: 700;
}

.room-error {
  min-height: 240px;
  gap: 10px;
  padding: 24px;
  border-radius: 8px;
  text-align: center;
}

.room-error ion-icon {
  font-size: 2.2rem;
}

.room-error h2 {
  margin: 0;
  color: #25211d;
}

.room-error p {
  margin: 0;
  color: #62584d;
}

.room-preview h2 {
  margin: 0 0 6px;
  color: #25211d;
  font-size: 1.12rem;
}

.room-preview p {
  margin: 0 0 8px;
  color: #62584d;
  font-size: 0.92rem;
  line-height: 1.45;
}

.room-preview strong {
  color: #2d5b47;
  font-size: 0.95rem;
}

@media (max-width: 560px) {
  .room-panel {
    padding: 16px;
  }

  .room-preview {
    grid-template-columns: 1fr;
  }

  .room-preview img,
  .image-fallback {
    max-width: none;
    height: 120px;
  }
}
</style>