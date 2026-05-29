<template>
  <div class="feature-card">
    <AppIcon :icon="icon" size="lg" />
    <h3>{{ title }}</h3>
    <p>{{ description }}</p>
  </div>
</template>

<script setup lang="ts">
import AppIcon from '@/components/atoms/AppIcon.vue';

defineProps<{
  icon: string;
  title: string;
  description: string;
}>();
</script>

<style scoped>
.feature-card {
  text-align: center;
}

.feature-card h3 {
  font-size: 1rem;
  font-weight: 600;
  margin-top: 12px;
  margin-bottom: 4px;
  color: #1c1917;
}

.feature-card p {
  font-size: 0.9rem;
  color: #57534e;
}
</style>

