<template>
  <PaddedPageTemplate>

    <header>
      <h1>Über das Boutique Hotel Technikum</h1>
      <p>Ein Ort, an dem Tradition auf Moderne trifft und Gastfreundschaft großgeschrieben wird.</p>
    </header>

    <section class="story-section">
      <div class="story-image">
        <img
          src="https://images.unsplash.com/photo-1775866914852-64e403d55a02?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwyfHxsdXh1cnklMjBib3V0aXF1ZSUyMGhvdGVsJTIwbG9iYnl8ZW58MXx8fHwxNzc3ODEzNTQwfDA&ixlib=rb-4.1.0&q=80&w=1080"
          alt=""
        />
      </div>
      <div class="story-text">
        <h2>Unsere Geschichte</h2>
        <p>
          Das Boutique Hotel Technikum wurde mit der Vision gegründet, moderne Technologie
          und herzliche Gastfreundschaft zu vereinen. Im Herzen von Wien gelegen, bieten
          wir seit vielen Jahren einen Ort der Ruhe und des Komforts für Reisende aus aller Welt.
        </p>
        <p>
          Unser Name spiegelt unsere Philosophie wider: technisch auf höchstem Niveau,
          aber immer mit dem menschlichen Touch, der ein Haus zu einem Zuhause macht.
          Wir kombinieren zeitgemäße Ausstattung mit persönlichem Service und schaffen
          so eine einzigartige Atmosphäre.
        </p>
        <p>
          Ob Sie geschäftlich oder privat reisen – wir freuen uns darauf, Ihren Aufenthalt
          in Wien zu einem unvergesslichen Erlebnis zu machen.
        </p>
      </div>
    </section>

    <section class="values-section">
      <h2>Unsere Werte</h2>
      <div class="values-grid">
        <ValueCard
          v-for="(value, idx) in values"
          :key="idx"
          :icon="value.icon"
          :title="value.title"
          :description="value.description"
        />
      </div>
    </section>

    <ContactCard
      title="Haben Sie Fragen?"
      description="Unser Team steht Ihnen gerne zur Verfügung und beantwortet alle Ihre Fragen."
      phone="+43 1 333 40 31"
      email="info@hotel-technikum.at"
    />
  </PaddedPageTemplate>
</template>

<script setup lang="ts">
import { heartOutline, leafOutline, peopleOutline, bulbOutline } from 'ionicons/icons';
import PaddedPageTemplate from '@/components/templates/PaddedPageTemplate.vue';
import ValueCard from '@/components/molecules/ValueCard.vue';
import ContactCard from '@/components/molecules/ContactCard.vue';

const values = [
  {
    icon: heartOutline,
    title: 'Persönliche Betreuung',
    description: 'Unser Team ist rund um die Uhr für Sie da und geht auf Ihre individuellen Wünsche ein.',
  },
  {
    icon: leafOutline,
    title: 'Nachhaltigkeit',
    description: 'Wir setzen auf umweltfreundliche Lösungen und regionale Partner für eine bessere Zukunft.',
  },
  {
    icon: peopleOutline,
    title: 'Komfort',
    description: 'Hochwertige Ausstattung und durchdachte Details sorgen für Ihr Wohlbefinden.',
  },
  {
    icon: bulbOutline,
    title: 'Moderne Ausstattung',
    description: 'Neueste Technologie und zeitgemäßes Design für einen angenehmen Aufenthalt.',
  },
];
</script>

<style scoped>
.story-image {
  border-radius: 0.75rem;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  margin-bottom: 1.5rem;
  aspect-ratio: 16 / 9;
}

.story-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.story-text p {
  color: #44403c;
  line-height: 1.7;
  margin-bottom: 0.75rem;
}

.values-section h2 {
  text-align: center;
}

.values-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1rem;
}

@media (min-width: 768px) {
  .values-grid {
    grid-template-columns: 1fr 1fr;
  }
}
</style>
