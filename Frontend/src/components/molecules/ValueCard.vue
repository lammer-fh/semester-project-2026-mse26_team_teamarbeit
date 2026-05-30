<template>
  <div class="value-card">
    <div class="value-card-inner">
      <AppIcon :icon="icon" size="md" />
      <div class="value-content">
        <h3>{{ title }}</h3>
        <p>{{ description }}</p>
      </div>
    </div>
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
.value-card {
  background: #ffffff;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.value-card-inner {
  display: flex;
  align-items: flex-start;
  gap: 16px;
}

.value-content h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: #1c1917;
  margin-bottom: 6px;
}

.value-content p {
  color: #57534e;
  line-height: 1.6;
  font-size: 0.95rem;
}
</style>

