<template>
  <section class="features-section">
    <SectionHeader :title="title" />
    <div class="features-grid">
      <FeatureCard
        v-for="(feature, idx) in features"
        :key="idx"
        :icon="feature.icon"
        :title="feature.title"
        :description="feature.description"
      />
    </div>
  </section>
</template>

<script setup lang="ts">
import SectionHeader from '@/components/atoms/SectionHeader.vue';
import FeatureCard from '@/components/molecules/FeatureCard.vue';

defineProps<{
  title: string;
  features: Array<{ icon: string; title: string; description: string }>;
}>();
</script>

<style scoped>
.features-section {
  padding: 48px 16px;
  background: #ffffff;
}

.features-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
  max-width: 900px;
  margin: 0 auto;
}

@media (min-width: 768px) {
  .features-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}
</style>

