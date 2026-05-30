<template>
  <div class="app-icon" :class="[sizeClass]">
    <ion-icon :icon="icon"></ion-icon>
  </div>
</template>
<script setup lang="ts">
import { IonIcon } from '@ionic/vue';
import { computed } from 'vue';
const props = withDefaults(defineProps<{
  icon: string;
  size?: 'sm' | 'md' | 'lg';
}>(), {
  size: 'md'
});
const sizeClass = computed(() => `icon-${props.size}`);
</script>
<style scoped>
.app-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: #fef3c7;
  border-radius: 8px;
  aspect-ratio: 1 / 1;
}
.app-icon ion-icon {
  color: var(--ion-color-primary-shade);
}
.icon-sm {
  width: 36px;
  height: 36px;
}
.icon-sm ion-icon {
  font-size: 18px;
}
.icon-md {
  width: 48px;
  height: 48px;
}
.icon-md ion-icon {
  font-size: 24px;
}
.icon-lg {
  width: 56px;
  height: 56px;
  border-radius: 50%;
}
.icon-lg ion-icon {
  font-size: 28px;
}
</style>
