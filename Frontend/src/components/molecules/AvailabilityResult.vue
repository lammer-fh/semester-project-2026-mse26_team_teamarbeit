<template>
  <div class="availability-result" :class="available ? 'available' : 'unavailable'">
    <ion-icon
        :icon="available ? checkmarkCircleOutline : closeCircleOutline"
        class="result-icon"
        aria-hidden="true"
    />
    <div class="result-body">
      <h3>{{ available ? 'Zimmer verfügbar!' : 'Zimmer nicht verfügbar' }}</h3>
      <p v-if="available">
        Das Zimmer ist im gewünschten Zeitraum verfügbar. Sie können jetzt mit der Buchung fortfahren.
      </p>
      <p v-else>
        Das Zimmer ist von {{ formatDate(period.from) }} bis {{ formatDate(period.to) }} leider nicht verfügbar.
      </p>
      <ion-button v-if="available" class="book-button" @click="$emit('book')">
        Jetzt buchen
      </ion-button>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, type PropType } from 'vue'
import { IonIcon } from '@ionic/vue'
import { checkmarkCircleOutline, closeCircleOutline } from 'ionicons/icons'

interface TravelPeriod {
  from: string
  to: string
}

export default defineComponent({
  name: 'AvailabilityResult',
  components: {
    IonIcon,
  },
  props: {
    available: {
      type: Boolean,
      required: true,
    },
    period: {
      type: Object as PropType<TravelPeriod>,
      required: true,
    },
  },
  emits: ['book'],
  data() {
    return {
      checkmarkCircleOutline,
      closeCircleOutline,
    }
  },
  methods: {
    formatDate(value: string): string {
      return new Intl.DateTimeFormat('de-AT', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
      }).format(new Date(`${value}T00:00:00`))
    },
  },
})
</script>

<style scoped>
.availability-result {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  padding: 24px;
  border-radius: 12px;
  border-width: 1px;
  border-style: solid;
}

.result-icon {
  font-size: 2rem;
  flex-shrink: 0;
  margin-top: 2px; /* optical alignment with text */
}

.result-body {
  flex: 1;
}

.result-body h3 {
  margin: 0 0 8px;
  font-size: 1.125rem;
  font-weight: 600;
}

.result-body p {
  margin: 0 0 16px;
  font-size: 0.9rem;
  line-height: 1.5;
}

.book-button {
  --background: #d97706;
  --background-hover: #b45309;
  --background-activated: #92400e;
  --border-radius: 8px;
  --box-shadow: none;
  --padding-start: 24px;
  --padding-end: 24px;
  height: 44px;
  font-weight: 600;
}

/* available */
.available {
  background: #f0fdf4;
  border-color: #bbf7d0;
}

.available .result-icon { color: #16a34a; }
.available h3 { color: #14532d; }
.available p  { color: #15803d; }

/* unavailable */
.unavailable {
  background: #fef2f2;
  border-color: #fecaca;
}

.unavailable .result-icon { color: #dc2626; }
.unavailable h3 { color: #7f1d1d; }
.unavailable p  { color: #b91c1c; }
</style>