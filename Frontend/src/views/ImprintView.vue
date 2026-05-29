<template>
  <PaddedPageTemplate>
    <h1 class="imprint-title">Impressum</h1>

    <div class="imprint-card">
      <LegalSection title="Angaben gemäß § 5 TMG">
        <p class="font-semibold">Boutique Hotel Technikum GmbH</p>
        <p>Höchstädtplatz 6</p>
        <p>1200 Wien</p>
        <p>Österreich</p>
      </LegalSection>

      <LegalSection title="Kontakt">
        <p><strong>Telefon:</strong> +43 1 333 40 31</p>
        <p><strong>E-Mail:</strong> info@hotel-technikum.at</p>
        <p><strong>Website:</strong> www.hotel-technikum.at</p>
      </LegalSection>

      <LegalSection title="Vertretungsberechtigte">
        <p>Geschäftsführung: Mag. Maria Schneider</p>
      </LegalSection>

      <LegalSection title="Handelsregister">
        <p><strong>Registergericht:</strong> Handelsgericht Wien</p>
        <p><strong>Registernummer:</strong> FN 123456a</p>
        <p><strong>UID-Nummer:</strong> ATU12345678</p>
      </LegalSection>

      <LegalSection title="Aufsichtsbehörde">
        <p>Magistrat der Stadt Wien</p>
        <p>Magistratsabteilung 63 - Gewerbebehörde</p>
      </LegalSection>

      <LegalSection title="Verantwortlich für den Inhalt">
        <p>Mag. Maria Schneider</p>
        <p>Höchstädtplatz 6</p>
        <p>1200 Wien, Österreich</p>
      </LegalSection>

      <LegalSection title="Haftungsausschluss">
        <div class="disclaimer-block">
          <h3>Haftung für Inhalte</h3>
          <p>
            Die Inhalte unserer Seiten wurden mit größter Sorgfalt erstellt. Für die
            Richtigkeit, Vollständigkeit und Aktualität der Inhalte können wir jedoch
            keine Gewähr übernehmen.
          </p>
        </div>
        <div class="disclaimer-block">
          <h3>Haftung für Links</h3>
          <p>
            Unser Angebot enthält Links zu externen Webseiten Dritter, auf deren Inhalte
            wir keinen Einfluss haben. Deshalb können wir für diese fremden Inhalte auch
            keine Gewähr übernehmen.
          </p>
        </div>
        <div class="disclaimer-block">
          <h3>Urheberrecht</h3>
          <p>
            Die durch die Seitenbetreiber erstellten Inhalte und Werke auf diesen Seiten
            unterliegen dem österreichischen Urheberrecht. Die Vervielfältigung, Bearbeitung,
            Verbreitung und jede Art der Verwertung außerhalb der Grenzen des Urheberrechtes
            bedürfen der schriftlichen Zustimmung des jeweiligen Autors bzw. Erstellers.
          </p>
        </div>
      </LegalSection>
    </div>
  </PaddedPageTemplate>
</template>

<script setup lang="ts">
import PaddedPageTemplate from '@/components/templates/PaddedPageTemplate.vue';
import LegalSection from '@/components/molecules/LegalSection.vue';
</script>

<style scoped>
.imprint-title {
  font-size: 1.75rem;
  font-weight: 700;
  color: #1c1917;
  margin-bottom: 1.5rem;
}

.imprint-card {
  background: #ffffff;
  border-radius: 12px;
  padding: 24px 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.disclaimer-block {
  margin-bottom: 1rem;
}

.disclaimer-block:last-child {
  margin-bottom: 0;
}

.disclaimer-block p {
  font-size: 0.9rem;
  line-height: 1.7;
}

@media (min-width: 768px) {
  .imprint-title {
    font-size: 2.25rem;
  }

  .imprint-card {
    padding: 32px;
  }
}
</style>