<template>
  <ion-page>
    <ion-content :fullscreen="true" class="page-content">
      <slot></slot>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonContent, IonPage } from '@ionic/vue';
</script>

<style scoped>
.page-content {
  --background: #ffffff;
}
</style>

