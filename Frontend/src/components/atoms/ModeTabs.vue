<template>
  <div class="mode-tabs" role="tablist" aria-label="Zeitraum auswahlen">
    <button
        v-for="mode in modes"
        :key="mode.value"
        type="button"
        role="tab"
        :aria-selected="modelValue === mode.value"
        class="mode-tab"
        :class="{ active: modelValue === mode.value }"
        @click="$emit('update:modelValue', mode.value)"
    >
      {{ mode.label }}
    </button>
  </div>
</template>

<script lang="ts">
import { defineComponent, type PropType } from 'vue'

export type ModeTabValue = 'exact' | 'preset' | 'nights'

export interface ModeTabOption {
  value: ModeTabValue
  label: string
}

export default defineComponent({
  name: 'ModeTabs',
  props: {
    modelValue: {
      type: String as PropType<ModeTabValue>,
      required: true,
    },
    modes: {
      type: Array as PropType<ModeTabOption[]>,
      required: true,
    },
  },
  emits: ['update:modelValue'],
})
</script>

<style scoped>
.mode-tabs {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 6px;
  padding: 5px;
  border: 1px solid #d8cfc3;
  border-radius: 8px;
  background: #f2ece3;
}

.mode-tab {
  min-height: 42px;
  border: 0;
  border-radius: 6px;
  background: transparent;
  color: #62584d;
  font: inherit;
  font-weight: 800;
}

.mode-tab.active {
  background: #ffffff;
  color: #2d5b47;
  box-shadow: 0 2px 8px rgba(52, 39, 25, 0.12);
}
</style>