<template>
  <section class="section-header">
    <h2>{{ title }}</h2>
    <p v-if="subtitle">{{ subtitle }}</p>
  </section>
</template>

<script setup lang="ts">
defineProps<{
  title: string;
  subtitle?: string;
}>();
</script>

<style scoped>
.section-header {
  text-align: center;
  margin-bottom: 32px;
}

.section-header h2 {
  font-size: 1.5rem;
  font-weight: 700;
  color: #1c1917;
  margin-bottom: 8px;
}

.section-header p {
  font-size: 1rem;
  color: #57534e;
  line-height: 1.6;
}

@media (min-width: 768px) {
  .section-header h2 {
    font-size: 1.875rem;
  }
}
</style>

