<template>
  <div class="period-summary">
    <ion-icon :icon="calendarOutline" aria-hidden="true" />
    <span>{{ label }}</span>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { IonIcon } from '@ionic/vue'
import { calendarOutline } from 'ionicons/icons'

export default defineComponent({
  name: 'PeriodSummary',
  components: {
    IonIcon,
  },
  props: {
    label: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      calendarOutline,
    }
  },
})
</script>

<style scoped>
.period-summary {
  display: flex;
  gap: 10px;
  align-items: center;
  padding: 13px 14px;
  border-radius: 8px;
  background: #f1f7f4;
  color: #315b47;
  font-weight: 700;
}

.period-summary ion-icon {
  font-size: 1.25rem;
}
</style>