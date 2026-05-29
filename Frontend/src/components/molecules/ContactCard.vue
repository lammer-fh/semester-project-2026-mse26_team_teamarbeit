<template>
  <section class="contact-cta">
    <h2>{{ title }}</h2>
    <p class="contact-description">{{ description }}</p>
    <div class="contact-details">
      <p><strong>Telefon:</strong> {{ phone }}</p>
      <p><strong>E-Mail:</strong> {{ email }}</p>
    </div>
  </section>
</template>

<script setup lang="ts">
defineProps<{
  title: string;
  description: string;
  phone: string;
  email: string;
}>();
</script>

<style scoped>
.contact-cta {
  background: #fffbeb;
  border-radius: 12px;
  padding: 32px 20px;
  text-align: center;
}

.contact-cta h2 {
  font-size: 1.5rem;
  font-weight: 700;
  color: #1c1917;
  margin-bottom: 12px;
}

.contact-description {
  color: #44403c;
  margin-bottom: 20px;
}

.contact-details p {
  color: #44403c;
  margin-bottom: 6px;
}
</style>

