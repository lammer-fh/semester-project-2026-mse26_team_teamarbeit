<template>
  <ion-button fill="clear" color="medium" :router-link="to">
    <ion-icon :icon="arrowBackOutline" slot="start" />
    <slot>Zurück</slot>
  </ion-button>
</template>

<script setup lang="ts">
import { IonButton, IonIcon } from '@ionic/vue';
import { arrowBackOutline } from 'ionicons/icons';

defineProps<{ to: string }>();
</script>