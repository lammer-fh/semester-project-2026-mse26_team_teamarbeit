<template>
  <aside class="card room-panel">
    <div v-if="room" class="room-preview">
      <div class="room-image-wrapper">
        <img
            v-if="room.imagePaths?.[0]"
            :src="room.imagePaths[0]"
            :alt="room.name"
            class="room-image"
        />
        <div v-else class="image-fallback">
          <ion-icon :icon="bedOutline" aria-hidden="true" />
        </div>
      </div>

      <div class="room-content">
        <h2>{{ room.name }}</h2>
        <p>{{ room.description }}</p>
        <RoomFeatureList v-if="mappedFeatures.length" :features="mappedFeatures" />
        <div class="room-footer">
          <PriceDisplay
              v-if="typeof room.pricePerNight === 'number'"
              :price="String(room.pricePerNight)"
          />
        </div>
      </div>
    </div>

    <div v-else-if="isLoading" class="room-loading">
      <ion-spinner name="crescent" />
      <span>Zimmer wird geladen</span>
    </div>

    <div v-else class="room-error">
      <ion-icon :icon="bedOutline" aria-hidden="true" />
      <h2>Zimmer nicht gefunden</h2>
      <p>Die Verfügbarkeit kann nur für ein gültiges Zimmer geprüft werden.</p>
    </div>
  </aside>
</template>

<script lang="ts">
import { defineComponent, type PropType } from 'vue'
import { IonIcon, IonSpinner } from '@ionic/vue'
import { bedOutline } from 'ionicons/icons'
import type { Room } from '@/models/room.model'
import RoomFeatureList from '@/components/molecules/RoomFeatureList.vue'
import PriceDisplay from '@/components/atoms/PriceDisplay.vue'
import { getIonicIcon } from '@/composables/getIonicIcon'

export default defineComponent({
  name: 'RoomInfoPanel',
  components: {
    IonIcon,
    IonSpinner,
    RoomFeatureList,
    PriceDisplay,
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
  computed: {
    mappedFeatures() {
      const { getIcon } = getIonicIcon()
      return (this.room?.roomExtras ?? []).map((extra) => ({
        label: extra.name,
        icon: getIcon('', extra.icon),
      }))
    },
  },
})
</script>

<style scoped>
/* panel shell — inherits .card border/shadow/bg from general.css */
.room-panel {
  overflow: hidden;
}

/* ── happy path ── */
.room-preview {
  display: grid;
  grid-template-columns: 240px 1fr;
}

.room-image-wrapper {
  position: relative;
  overflow: hidden;
  background: #f5f5f4;
}

.room-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 220ms ease;
}

.room-panel:hover .room-image {
  transform: scale(1.04);
}

.image-fallback {
  display: grid;
  place-items: center;
  width: 100%;
  height: 100%;
  min-height: 160px;
  background: #f5f5f4;
  color: #a8a29e;
  font-size: 2.2rem;
}

.room-content {
  display: flex;
  flex-direction: column;
  padding: 1.5rem;
}

.room-content h2 {
  margin: 0 0 0.5rem;
  color: #1c1917;
  font-size: 1.25rem;
  font-weight: 700;
  line-height: 1.4;
}

.room-content p {
  margin: 0 0 1rem;
  color: #57534e;
  font-size: 0.92rem;
  line-height: 1.625;
}

.room-footer {
  margin-top: auto;
  padding-top: 1rem;
}

/* ── loading state ── */
.room-loading {
  display: grid;
  place-items: center;
  gap: 12px;
  min-height: 160px;
  color: #57534e;
  font-weight: 600;
}

/* ── error state ── */
.room-error {
  display: grid;
  place-items: center;
  gap: 10px;
  min-height: 160px;
  padding: 24px;
  text-align: center;
  color: #57534e;
  font-size: 2.2rem;
}

.room-error h2 {
  margin: 0;
  font-size: 1.1rem;
  color: #1c1917;
}

.room-error p {
  margin: 0;
  font-size: 0.9rem;
  color: #57534e;
}

/* ── responsive ── */
@media (max-width: 640px) {
  .room-preview {
    grid-template-columns: 1fr;
  }

  .room-image-wrapper {
    height: 180px;
  }
}
</style>