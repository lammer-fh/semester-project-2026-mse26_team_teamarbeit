<template>
  <nav v-if="totalPages > 1" class="pagination" aria-label="Seitennavigation">
    <ion-button
        size="default"
        fill="outline"
        :disabled="currentPage === 1"
        aria-label="Vorherige Seite"
        @click="$emit('page-change', currentPage - 1)"
    >
      <IonIcon slot="icon-only" :icon="chevronBack"></IonIcon>
    </ion-button>

    <ion-button
        v-for="page in totalPages"
        :key="page"
        size="default"
        :fill="currentPage === page ? 'solid' : 'outline'"
        @click="$emit('page-change', page)"
    >
      {{ page }}
    </ion-button>

    <ion-button
        size="default"
        fill="outline"
        :disabled="currentPage === totalPages"
        aria-label="Nächste Seite"
        @click="$emit('page-change', currentPage + 1)"
    >
      <IonIcon slot="icon-only" :icon="chevronForward"></IonIcon>
    </ion-button>
  </nav>
</template>

<script setup lang="ts">
import { IonButton, IonIcon } from '@ionic/vue';
import { chevronBack, chevronForward } from 'ionicons/icons';

defineProps<{
  currentPage: number;
  totalPages: number;
}>();

defineEmits<{
  'page-change': [page: number];
}>();
</script>

<style scoped>
.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}
</style>