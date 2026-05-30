<template>
  <section class="cta-section">
    <h2>{{ title }}</h2>
    <p>{{ description }}</p>
    <slot></slot>
  </section>
</template>

<script setup lang="ts">
defineProps<{
  title: string;
  description: string;
}>();
</script>

<style scoped>
.cta-section {
  padding: 48px 16px;
  background: var(--ion-color-primary-shade);
  text-align: center;
  color: white;
}

.cta-section h2 {
  color: #ffffff;
}

.cta-section p {
  font-size: 1rem;
  margin-bottom: 24px;
  color: #fef3c7;
}

@media (min-width: 768px) {
  .cta-section h2 {
    font-size: 1.875rem;
  }

  .cta-section p {
    font-size: 1.15rem;
  }
}
</style>

