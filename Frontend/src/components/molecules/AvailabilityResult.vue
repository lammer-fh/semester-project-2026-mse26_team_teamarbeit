<template>
  <div class="availability-result" :class="available ? 'available' : 'unavailable'">
    <ion-icon :icon="available ? checkmarkCircleOutline : closeCircleOutline" aria-hidden="true" />
    <div>
      <h3>{{ available ? 'Zimmer ist verfügbar' : 'Zimmer ist nicht verfügbar' }}</h3>
      <p>{{ formatDate(period.from) }} bis {{ formatDate(period.to) }}</p>
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
  display: grid;
  grid-template-columns: auto 1fr;
  gap: 14px;
  padding: 16px;
  border-radius: 8px;
}

.availability-result ion-icon {
  font-size: 2rem;
}

.availability-result h3 {
  margin: 0 0 4px;
  font-size: 1.05rem;
}

.availability-result p {
  margin: 0;
}

.availability-result.available {
  border: 1px solid #b9dec9;
  background: #eef9f2;
  color: #28603b;
}

.availability-result.unavailable {
  border: 1px solid #efc0b7;
  background: #fff1ee;
  color: #943525;
}
</style>