<template>
  <div class="features-grid">
    <IconPill
        v-for="feature in features"
        :key="feature.label"
        :icon="feature.icon"
        :label="feature.label"
    />
  </div>
</template>

<script setup lang="ts">
import IconPill from '@/components/atoms/IconPill.vue';

defineProps<{
  features: { label: string; icon: string }[];
}>();
</script>

<style scoped>
.features-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #e7e5e4;
}
</style>