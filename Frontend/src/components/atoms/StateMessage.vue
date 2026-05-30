<template>
  <p :class="['rooms-state', { 'rooms-state-error': error }]">
    <slot />
  </p>
</template>

<script setup lang="ts">
defineProps<{ error?: boolean }>();
</script>

<style scoped>
.rooms-state {
  margin: 2rem 0 3rem;
  color: #57534e;
  text-align: center;
  font-size: 1rem;
}
.rooms-state-error {
  color: #b91c1c;
}
</style>