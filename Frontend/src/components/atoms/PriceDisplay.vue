<template>
  <div class="room-price">
    <span class="price-amount">€{{ price }}</span>
    <span class="price-label">/ Nacht</span>
  </div>
</template>

<script setup lang="ts">
defineProps<{ price: string }>();
</script>

<style scoped>
.room-price {
  display: flex;
  align-items: baseline;
  gap: 0.25rem;
}
.price-amount {
  font-size: 1.75rem;
  font-weight: 500;
  color: var(--ion-color-primary-shade);
}
.price-label {
  font-size: 0.875rem;
  color: #78716c;
}
</style>
