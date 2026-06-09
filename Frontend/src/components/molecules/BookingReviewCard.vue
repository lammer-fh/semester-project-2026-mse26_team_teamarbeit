<template>
  <section class="review-card">
    <div class="review-card-header">
      <h3>{{ title }}</h3>
      <ion-button
        v-if="editable"
        type="button"
        fill="clear"
        size="small"
        class="edit-button"
        @click="$emit('edit')"
      >
        <ion-icon :icon="createOutline" slot="start" aria-hidden="true" />
        Ändern
      </ion-button>
    </div>

    <slot />
  </section>
</template>

<script setup lang="ts">
import { IonButton, IonIcon } from '@ionic/vue'
import { createOutline } from 'ionicons/icons'

defineProps<{
  title: string
  editable?: boolean
}>()

defineEmits<{
  edit: []
}>()
</script>

<style scoped>
.review-card {
  display: grid;
  gap: 18px;
  padding: 24px;
  border: 1px solid #e7e0d8;
  border-radius: 8px;
  background: #ffffff;
  box-shadow: 0 10px 24px rgba(52, 39, 25, 0.07);
}

.review-card-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 16px;
}

h3 {
  margin: 0;
  color: #1c1917;
  font-size: 1.15rem;
  font-weight: 800;
}

.edit-button {
  min-height: 32px;
  margin: -6px -8px 0 0;
  color: #b45309;
  font-weight: 800;
  --padding-start: 8px;
  --padding-end: 8px;
}

.edit-button ion-icon {
  font-size: 1rem;
}
</style>
