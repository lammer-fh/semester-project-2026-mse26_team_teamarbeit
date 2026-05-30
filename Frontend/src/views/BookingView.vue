<template>
  <PaddedPageTemplate>
    <BackButton to="/rooms">Zurück zur Übersicht</BackButton>

    <div class="placeholder-container">
      <div class="placeholder-card card">
        <ion-icon :icon="constructOutline" class="placeholder-icon" aria-hidden="true" />
        <h1>Buchung kommt bald</h1>
        <p>
          Die Online-Buchung befindet sich derzeit in Entwicklung.
          Bitte kontaktieren Sie uns direkt für Reservierungen.
        </p>
        <div class="placeholder-actions">
          <ion-button :router-link="`/rooms/${roomId}`" fill="outline">
            Zurück zum Zimmer
          </ion-button>
          <ion-button router-link="/rooms">
            Alle Zimmer ansehen
          </ion-button>
        </div>
      </div>
    </div>
  </PaddedPageTemplate>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { IonButton, IonIcon } from '@ionic/vue'
import { constructOutline } from 'ionicons/icons'
import PaddedPageTemplate from '@/components/templates/PaddedPageTemplate.vue'
import BackButton from '@/components/molecules/BackButton.vue'

export default defineComponent({
  name: 'BookingVue',
  components: {
    PaddedPageTemplate,
    BackButton,
    IonButton,
    IonIcon,
  },
  data() {
    return {
      constructOutline,
      roomId: '',
    }
  },
  mounted() {
    const id = this.$route.params.id
    this.roomId = typeof id === 'string' ? id : ''
  },
})
</script>

<style scoped>
.placeholder-container {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding: 2rem 0;
}

.placeholder-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 3rem 2rem;
  max-width: 480px;
  width: 100%;
}

.placeholder-icon {
  font-size: 4rem;
  color: #d97706;
  margin-bottom: 1.5rem;
}

.placeholder-card h1 {
  margin: 0 0 1rem;
}

.placeholder-card p {
  color: #57534e;
  line-height: 1.7;
  margin: 0 0 2rem;
}

.placeholder-actions {
  display: grid;
  grid-template-columns: 1fr;
  gap: 0.75rem;
  width: 100%;
}

@media (min-width: 480px) {
  .placeholder-actions {
    grid-template-columns: 1fr 1fr;
  }
}
</style>