<template>
  <div class="carousel">
    <div class="carousel-track">
      <img :src="images[currentIndex]" :alt="`${alt} - Bild ${currentIndex + 1}`" class="carousel-image" />
    </div>

    <button v-if="images.length > 1" class="carousel-btn carousel-btn-left" @click="prev">
      <ion-icon :icon="chevronBackOutline" />
    </button>
    <button v-if="images.length > 1" class="carousel-btn carousel-btn-right" @click="next">
      <ion-icon :icon="chevronForwardOutline" />
    </button>

    <div v-if="images.length > 1" class="carousel-dots">
      <span
          v-for="(_, i) in images"
          :key="i"
          :class="['dot', { active: i === currentIndex }]"
          @click="currentIndex = i"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { IonIcon } from '@ionic/vue';
import { chevronBackOutline, chevronForwardOutline } from 'ionicons/icons';

const props = defineProps<{
  images: string[];
  alt: string;
}>();

const currentIndex = ref(0);

const prev = () => {
  currentIndex.value = currentIndex.value === 0 ? props.images.length - 1 : currentIndex.value - 1;
};

const next = () => {
  currentIndex.value = currentIndex.value === props.images.length - 1 ? 0 : currentIndex.value + 1;
};
</script>

<style scoped>
.carousel {
  position: relative;
  aspect-ratio: 16 / 9;
  border-radius: 0.75rem;
  overflow: hidden;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
  margin-bottom: 2rem;
}

.carousel-track {
  width: 100%;
  height: 100%;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.carousel-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: rgba(255, 255, 255, 0.85);
  border: none;
  border-radius: 50%;
  width: 2.5rem;
  height: 2.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  font-size: 1.2rem;
  color: #1c1917;
  transition: background 0.2s ease;
  z-index: 1;
}

.carousel-btn:hover {
  background: rgba(255, 255, 255, 1);
}

.carousel-btn-left  { left: 0.75rem; }
.carousel-btn-right { right: 0.75rem; }

.carousel-dots {
  position: absolute;
  bottom: 0.75rem;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 0.4rem;
}

.dot {
  width: 0.5rem;
  height: 0.5rem;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.6);
  cursor: pointer;
  transition: background 0.2s ease;
}

.dot.active {
  background: #ffffff;
}
</style>