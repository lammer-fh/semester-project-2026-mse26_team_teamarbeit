<template>
  <dl class="summary-list">
    <div v-for="item in items" :key="item.label" class="summary-row">
      <dt>{{ item.label }}</dt>
      <dd>{{ item.value }}</dd>
    </div>
  </dl>
</template>

<script setup lang="ts">
interface BookingSummaryItem {
  label: string
  value: string
}

defineProps<{
  items: BookingSummaryItem[]
}>()
</script>

<style scoped>
.summary-list {
  display: grid;
  gap: 0;
  margin: 0;
  border: 1px solid #e2d8cc;
  border-radius: 8px;
  background: #ffffff;
  overflow: hidden;
}

.summary-row {
  display: grid;
  grid-template-columns: minmax(120px, 0.42fr) 1fr;
  gap: 14px;
  padding: 14px 16px;
  border-bottom: 1px solid #eee7df;
}

.summary-row:last-child {
  border-bottom: 0;
}

dt {
  color: #6b6257;
  font-size: 0.9rem;
  font-weight: 700;
}

dd {
  margin: 0;
  color: #2e2923;
  font-weight: 800;
  line-height: 1.45;
  overflow-wrap: anywhere;
}

@media (max-width: 520px) {
  .summary-row {
    grid-template-columns: 1fr;
    gap: 4px;
  }
}
</style>
