<template>
  <ion-page>
    <ion-content :fullscreen="true" class="page-content">
      <div class="page-container">
        <slot></slot>
      </div>
      <Footer/>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonContent, IonPage} from '@ionic/vue';
import Footer from '@/components/organisms/Footer.vue'
</script>

<style scoped>
.page-content {
  --padding-top: 56px;
}

.page-container {
  padding: 0 0 16px;
  margin: 0 auto;
}
</style>

