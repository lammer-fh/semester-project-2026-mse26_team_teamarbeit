<template>
  <section class="legal-section">
    <h2>{{ title }}</h2>
    <div class="legal-content">
      <slot></slot>
    </div>
  </section>
</template>

<script setup lang="ts">
defineProps<{
  title: string;
}>();
</script>

<style scoped>
.legal-section {
  margin-bottom: 0;
}

.legal-section h2 {
  font-size: 1.15rem;
  font-weight: 600;
  color: #1c1917;
  margin-bottom: 0.75rem;
}

.legal-content {
  color: #44403c;
  line-height: 1.6;
}

.legal-content :deep(p) {
  margin-bottom: 0.4rem;
}

.legal-content :deep(h3) {
  font-weight: 600;
  margin-bottom: 0.4rem;
  color: #1c1917;
}
</style>

