<template>
  <ion-page>
    <ion-content :fullscreen="true" class="page-content">
      <div class="padded-page-container">
        <slot></slot>
      </div>
      <Footer/>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonContent, IonPage } from '@ionic/vue';
import Footer from "@/components/organisms/Footer.vue";
</script>

<style scoped>
.page-content {
  --padding-top: 56px;
}

.padded-page-container {
  padding: 16px;
  max-width: 800px;
  margin: 0 auto;
}
</style>

