<template>
  <ion-page>
    <ion-content :fullscreen="true" class="padded-page-content">
      <div class="page-container">
        <slot></slot>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonContent, IonPage } from '@ionic/vue';
</script>

<style scoped>
.padded-page-content {
  --background: #ffffff;
  --padding-top: 16px;
}

.page-container {
  padding: 16px;
  padding-top: 3rem;
  max-width: 800px;
  margin: 0 auto;
}
</style>

