<template>
  <PageTemplate>

    <HeroSection
      title="Boutique Hotel Technikum"
      subtitle="Smart comfort in the heart of Vienna"
      image-src="/img/home1.jpg"
      image-alt="Hotel Lobby"
    >
      <ion-button :router-link="`/rooms`" size="large">
        Zimmer entdecken
      </ion-button>
    </HeroSection>

    <FeaturesSection
      title="Ihr Komfort ist unsere Priorität"
      :features="features"
    />

    <section class="about-section">
      <div class="about-grid">
        <div class="about-text">
          <h2>Willkommen im Boutique Hotel Technikum</h2>
          <p>
            Unser Hotel verbindet moderne Technologie mit herzlicher Gastfreundschaft.
            In zentraler Lage in Wien bieten wir Ihnen den perfekten Ausgangspunkt
            für Ihre geschäftlichen oder privaten Aufenthalte.
          </p>
          <p>
            Jedes unserer Zimmer ist sorgfältig gestaltet, um höchsten Komfort und
            Funktionalität zu bieten. Von schnellem WLAN über ergonomische Arbeitsplätze
            bis hin zu hochwertigen Betten – bei uns finden Sie alles, was Sie brauchen.
          </p>
          <p>
            Nachhaltigkeit und persönliche Betreuung stehen im Mittelpunkt unserer
            Philosophie. Wir freuen uns darauf, Sie bei uns begrüßen zu dürfen.
          </p>
          <ion-button fill="outline" :router-link="`/about`" size="medium" class="about-btn">
            Mehr über uns
          </ion-button>
        </div>
        <div class="about-image">
          <img
            src="/img/home2.jpg"
            alt="Hotel Interior"
          />
        </div>
      </div>
    </section>

    <CtaSection
      title="Bereit für Ihren Aufenthalt?"
      description="Entdecken Sie unsere komfortablen Zimmer und buchen Sie noch heute."
    >
      <ion-button color="light" :router-link="`/rooms`" size="large">
        Zimmer ansehen
      </ion-button>
    </CtaSection>
  </PageTemplate>
</template>

<script setup lang="ts">
import { IonButton } from '@ionic/vue';
import { wifiOutline, cafeOutline, locationOutline, sparklesOutline } from 'ionicons/icons';
import PageTemplate from '@/components/templates/PageTemplate.vue';
import HeroSection from '@/components/organisms/HeroSection.vue';
import FeaturesSection from '@/components/organisms/FeaturesSection.vue';
import CtaSection from '@/components/organisms/CtaSection.vue';

const features = [
  { icon: wifiOutline, title: 'High-Speed WLAN', description: 'Kostenlos in allen Zimmern' },
  { icon: cafeOutline, title: 'Frühstücksbuffet', description: 'Täglich frisch und regional' },
  { icon: locationOutline, title: 'Zentrale Lage', description: 'Im Herzen von Wien' },
  { icon: sparklesOutline, title: 'Moderner Komfort', description: 'Zeitgemäße Ausstattung' },
];
</script>

<style scoped>
.about-section {
  padding: 3rem 1rem;
  background: #fafaf9;
}

.about-grid {
  max-width: 900px;
  margin: 0 auto;
}

.about-text h2 {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 1rem;
  color: #1c1917;
}

.about-text p {
  color: #44403c;
  line-height: 1.7;
  margin-bottom: 0.75rem;
}

.about-btn {
  margin-top: 1.25rem;
}

.about-image {
  margin-top: 1.5rem;
  border-radius: 0.75rem;
  overflow: hidden;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
  aspect-ratio: 4 / 3;
}

.about-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

@media (min-width: 768px) {
  .about-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 3rem;
    align-items: center;
  }

  .about-image {
    margin-top: 0;
  }

  .about-text h2 {
    font-size: 1.875rem;
  }
}
</style>
