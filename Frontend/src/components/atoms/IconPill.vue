<template>
  <li class="icon-pill">
    <IonIcon :icon="icon" aria-hidden="true"/>
    <span>{{ label }}</span>
  </li>
</template>

<script setup lang="ts">
import { IonIcon } from '@ionic/vue';

defineProps<{
  label: string;
  icon: string;
}>();
</script>

<style scoped>
.icon-pill {
  display: inline-flex;
  align-items: center;
  gap: 0.35rem;
  padding: 0.4rem 0.7rem;
  border-radius: 999px;
  background: #f5f5f4;
  color: #44403c;
  font-size: 0.8125rem;
  font-weight: 500;
}

.icon-pill ion-icon {
  color: var(--ion-color-primary);
  font-size: 1rem;
  flex-shrink: 0;
}
</style>