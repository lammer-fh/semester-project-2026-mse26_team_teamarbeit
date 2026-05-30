<template>
  <section class="hero-section">
    <div class="hero-image">
      <img :src="imageSrc" :alt="imageAlt" />
      <div class="hero-overlay"></div>
    </div>
    <div class="hero-content">
      <h1>{{ title }}</h1>
      <p>{{ subtitle }}</p>
      <slot></slot>
    </div>
  </section>
</template>

<script setup lang="ts">
defineProps<{
  title: string;
  subtitle: string;
  imageSrc: string;
  imageAlt?: string;
}>();
</script>

<style scoped>
.hero-section {
  position: relative;
  height: 70vh;
  min-height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.hero-image {
  position: absolute;
  inset: 0;
}

.hero-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.hero-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(to right, rgba(28, 25, 23, 0.7), rgba(28, 25, 23, 0.4));
}

.hero-content {
  position: relative;
  z-index: 10;
  text-align: center;
  color: white;
  padding: 16px;
  max-width: 800px;
  margin: 0 auto;
}

.hero-content h1 {
  color: #ffffff;
  font-weight: 700;
  margin-bottom: 16px;
}

.hero-content p {
  font-size: 1.15rem;
  margin-bottom: 24px;
  color: #e7e5e4;
}

@media (min-width: 768px) {
  .hero-content h1 {
    font-size: 3.5rem;
  }

  .hero-content p {
    font-size: 1.75rem;
  }
}
</style>

