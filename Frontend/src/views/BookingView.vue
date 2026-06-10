<template>
  <PaddedPageTemplate>
    <BackButton :to="`/availability/${roomId}`">Zurück zur Verfügbarkeit</BackButton>

    <div class="booking-container">
      <header>
        <h1>Zimmer buchen</h1>
        <p>Prüfen Sie Ihre Reisedaten und geben Sie die Gastdaten für die Buchung ein.</p>
      </header>

      <section class="booking-grid" aria-label="Buchung">
        <room-info-panel
          v-if="step === 'form' && !isConfirmed"
          :room="roomStore.currentRoom"
          :is-loading="roomStore.isLoading"
        />

        <article v-if="isConfirmed && bookingStore.currentBooking" class="booking-panel confirmation-layout">

          <div class="confirmation-hero">
            <div class="confirmation-icon-wrap">
              <ion-icon :icon="checkmarkCircleOutline" class="confirmation-icon" aria-hidden="true" />
            </div>
            <h2 class="confirmation-title">Buchung erfolgreich!</h2>
            <p class="confirmation-subtitle">
              Vielen Dank für Ihre Buchung. Eine Bestätigung wurde an
              <strong>{{ form.email }}</strong> gesendet.
            </p>
          </div>

          <div class="booking-number-card">
            <p class="booking-number-label">Ihre Buchungsnummer</p>
            <p class="booking-number-value">{{ bookingStore.currentBooking.id }}</p>
          </div>

          <div class="confirmation-section">
            <h3 class="confirmation-section-title">Buchungsdetails</h3>
            <div class="review-room">
              <img
                  v-if="reviewRoomImage"
                  :src="reviewRoomImage"
                  :alt="roomStore.currentRoom?.name ?? 'Zimmer'"
                  class="review-room-image"
              />
              <div v-else class="review-room-fallback">
                <ion-icon :icon="bedOutline" aria-hidden="true" />
              </div>
              <div class="review-room-content">
                <h4>{{ roomStore.currentRoom?.name ?? 'Gebuchtes Zimmer' }}</h4>
                <p>{{ roomStore.currentRoom?.description }}</p>
                <RoomFeatureList v-if="reviewRoomFeatures.length" :features="reviewRoomFeatures" />
              </div>
            </div>
            <div class="confirmation-details-list">
              <p><strong>Anreise:</strong> {{ formatLongDate(bookingStore.currentBooking.from) }}</p>
              <p><strong>Abreise:</strong> {{ formatLongDate(bookingStore.currentBooking.to) }}</p>
              <p><strong>Aufenthaltsdauer:</strong> {{ confirmedNightsText }}</p>
              <p><strong>Gast:</strong> {{ guestName }}</p>
              <p><strong>E-Mail:</strong> {{ form.email }}</p>
              <p><strong>Frühstück:</strong> {{ bookingStore.currentBooking.breakfast ? 'Ja, inklusive' : 'Nein' }}</p>
            </div>
          </div>

          <div class="confirmation-section">
            <h3 class="confirmation-section-title">Check-in Informationen</h3>
            <div class="review-text-list">
              <p><strong>Check-in:</strong> ab 15:00 Uhr</p>
              <p><strong>Check-out:</strong> bis 11:00 Uhr</p>
              <p>Bitte halten Sie bei Ihrer Ankunft einen gültigen Lichtbildausweis und Ihre Buchungsbestätigung bereit.</p>
            </div>
          </div>

          <div class="confirmation-section">
            <h3 class="confirmation-section-title">Kontakt</h3>
            <div class="contact-option-grid">
              <a href="tel:+43133340031" class="contact-option">
                <ion-icon :icon="callOutline" aria-hidden="true" />
                <span><small>Telefon</small>+43 1 333 40 31</span>
              </a>
              <a href="mailto:info@hotel-technikum.at" class="contact-option">
                <ion-icon :icon="mailOutline" aria-hidden="true" />
                <span><small>E-Mail</small>info@hotel-technikum.at</span>
              </a>
            </div>
          </div>

          <div class="confirmation-section">
            <h3 class="confirmation-section-title">Anfahrt</h3>
            <div class="map-wrap">
              <iframe
                  title="Hotelstandort"
                  src="https://www.openstreetmap.org/export/embed.html?bbox=16.364%2C48.221%2C16.374%2C48.231&layer=mapnik&marker=48.2258%2C16.3694"
                  class="map-frame"
                  loading="lazy"
              />
            </div>
            <div class="review-text-list">
              <p><strong>Adresse:</strong> Höchstädtplatz 6, 1200 Wien</p>
              <p><strong>U-Bahn:</strong> U6 bis Dresdner Straße (5 Min. Fußweg)</p>
              <p><strong>Straßenbahn:</strong> Linie 2 bis Höchstädtplatz</p>
              <p><strong>Vom Hauptbahnhof:</strong> ca. 35 Min. mit öffentlichen Verkehrsmitteln</p>
            </div>
          </div>

          <div class="panel-actions">
            <ion-button router-link="/home" expand="block">Zur Startseite</ion-button>
            <ion-button fill="outline" expand="block" @click="printBooking">
              <ion-icon :icon="printOutline" slot="start" aria-hidden="true" />
              Buchung drucken
            </ion-button>
          </div>
        </article>

        <form v-else-if="step === 'form'" class="booking-panel" @submit.prevent="reviewBooking">
          <div class="panel-header">
            <p class="eyebrow">Schritt 1 von 2</p>
            <h2>Ihre Daten</h2>
          </div>

          <div class="period-card">
            <PeriodSummary :label="periodLabel" />
            <ion-button type="button" fill="clear" :router-link="`/availability/${roomId}`">
              Zeitraum ändern
            </ion-button>
          </div>

          <div class="field-grid">
            <div class="field-group">
              <label class="field-label" for="first-name">Vorname</label>
              <ion-input
                id="first-name"
                v-model.trim="form.firstName"
                autocomplete="given-name"
                @ionInput="clearFieldError('firstName')"
              />
              <p v-if="validationErrors.firstName" class="field-error">{{ validationErrors.firstName }}</p>
            </div>

            <div class="field-group">
              <label class="field-label" for="last-name">Nachname</label>
              <ion-input
                id="last-name"
                v-model.trim="form.lastName"
                autocomplete="family-name"
                @ionInput="clearFieldError('lastName')"
              />
              <p v-if="validationErrors.lastName" class="field-error">{{ validationErrors.lastName }}</p>
            </div>
          </div>

          <div class="field-grid">
            <div class="field-group">
              <label class="field-label" for="email">E-Mail</label>
              <ion-input
                id="email"
                v-model.trim="form.email"
                type="email"
                autocomplete="email"
                @ionInput="clearFieldError('email')"
              />
              <p v-if="validationErrors.email" class="field-error">{{ validationErrors.email }}</p>
            </div>

            <div class="field-group">
              <label class="field-label" for="confirm-email">E-Mail bestätigen</label>
              <ion-input
                id="confirm-email"
                v-model.trim="form.confirmEmail"
                type="email"
                autocomplete="email"
                @ionInput="clearFieldError('confirmEmail')"
              />
              <p v-if="validationErrors.confirmEmail" class="field-error">{{ validationErrors.confirmEmail }}</p>
            </div>
          </div>

          <div class="breakfast-row">
            <div>
              <label class="field-label" for="breakfast">Frühstück</label>
              <p>Frühstück zum Aufenthalt hinzufügen.</p>
            </div>
            <ion-toggle id="breakfast" v-model="form.breakfast">
              {{ form.breakfast ? 'Ja' : 'Nein' }}
            </ion-toggle>
          </div>

          <p v-if="periodError" class="api-error">{{ periodError }}</p>
          <p v-if="roomStore.error" class="api-error">{{ roomStore.error }}</p>

          <ion-button
            type="submit"
            expand="block"
            size="large"
            :disabled="roomStore.isLoading || !roomStore.currentRoom || !hasValidPeriod"
          >
            Buchung prüfen
          </ion-button>
        </form>

        <article v-else class="booking-panel">
          <div class="panel-header">
            <p class="eyebrow">Schritt 2 von 2</p>
            <h2>Buchung prüfen</h2>
          </div>

          <div class="review-card-stack">
            <BookingReviewCard title="Zimmerdetails" editable @edit="goToRoomDetails">
              <div class="review-room">
                <img
                  v-if="reviewRoomImage"
                  :src="reviewRoomImage"
                  :alt="roomStore.currentRoom?.name ?? 'Zimmer'"
                  class="review-room-image"
                />
                <div v-else class="review-room-fallback">
                  <ion-icon :icon="bedOutline" aria-hidden="true" />
                </div>

                <div class="review-room-content">
                  <h4>{{ roomStore.currentRoom?.name ?? 'Zimmer wird geladen' }}</h4>
                  <p>{{ roomStore.currentRoom?.description }}</p>
                  <RoomFeatureList v-if="reviewRoomFeatures.length" :features="reviewRoomFeatures" />
                </div>
              </div>
            </BookingReviewCard>

            <BookingReviewCard title="Buchungszeitraum" editable @edit="goToAvailability">
              <div class="review-text-list">
                <p><strong>Anreise:</strong> {{ formatLongDate(from) }}</p>
                <p><strong>Abreise:</strong> {{ formatLongDate(to) }}</p>
                <p><strong>Aufenthaltsdauer:</strong> {{ nightsText }}</p>
              </div>
            </BookingReviewCard>

            <BookingReviewCard title="Persönliche Daten" editable @edit="step = 'form'">
              <div class="review-text-list">
                <p><strong>Name:</strong> {{ guestName }}</p>
                <p><strong>E-Mail:</strong> {{ form.email }}</p>
                <p><strong>Frühstück:</strong> {{ form.breakfast ? 'Ja, inklusive' : 'Nein' }}</p>
              </div>
            </BookingReviewCard>

            <BookingReviewCard title="Preiszusammenfassung" class="price-review-card">
              <div class="price-summary">
                <div class="price-row">
                  <span>Zimmer ({{ nightsText }})</span>
                  <span>{{ formatCurrency(roomSubtotal) }}</span>
                </div>
                <div class="price-row">
                  <span>Frühstück ({{ breakfastDaysText }})</span>
                  <span>{{ form.breakfast ? formatCurrency(breakfastSubtotal) : formatCurrency(0) }}</span>
                </div>
                <div class="price-row total">
                  <span>Gesamtpreis</span>
                  <span>{{ formatCurrency(estimatedTotal) }}</span>
                </div>
              </div>
            </BookingReviewCard>
          </div>

          <div v-if="bookingStore.error" class="booking-error-card">
            <ion-icon :icon="alertCircleOutline" aria-hidden="true" />
            <div>
              <h3>Buchung fehlgeschlagen</h3>
              <p>{{ bookingStore.error }}</p>
              <p>Bitte prüfen Sie Ihre Angaben oder versuchen Sie es später erneut. Unser Team hilft Ihnen auch direkt per Telefon weiter.</p>
            </div>
          </div>

          <div class="panel-actions">
            <ion-button type="button" fill="outline" expand="block" @click="step = 'form'">
              Daten ändern
            </ion-button>
            <ion-button
              type="button"
              expand="block"
              :disabled="bookingStore.isLoading"
              @click="submitBooking"
            >
              <ion-spinner v-if="bookingStore.isLoading" name="crescent" />
              <span v-else>Jetzt verbindlich buchen</span>
            </ion-button>
          </div>
        </article>
      </section>
    </div>
  </PaddedPageTemplate>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import {
  IonButton,
  IonIcon,
  IonInput,
  IonSpinner,
  IonToggle,
} from '@ionic/vue'
import {
  alertCircleOutline,
  bedOutline,
  callOutline,
  checkmarkCircleOutline,
  locationOutline,
  mailOutline,
  printOutline,
  timeOutline,
  trainOutline,
} from 'ionicons/icons'
import PaddedPageTemplate from '@/components/templates/PaddedPageTemplate.vue'
import BackButton from '@/components/molecules/BackButton.vue'
import PeriodSummary from '@/components/molecules/PeriodSummary.vue'
import BookingSummaryList from '@/components/molecules/BookingSummaryList.vue'
import BookingReviewCard from '@/components/molecules/BookingReviewCard.vue'
import RoomFeatureList from '@/components/molecules/RoomFeatureList.vue'
import RoomInfoPanel from '@/components/organisms/RoomInfoPanel.vue'
import { useRoomStore } from '@/stores/room.store'
import { useBookingStore } from '@/stores/booking.store'
import { getIonicIcon } from '@/composables/getIonicIcon'

interface BookingForm {
  firstName: string
  lastName: string
  email: string
  confirmEmail: string
  breakfast: boolean
}

interface BookingSummaryItem {
  label: string
  value: string
}

type BookingStep = 'form' | 'review'
type BookingValidationErrors = Partial<Record<keyof Omit<BookingForm, 'breakfast'>, string>>

const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
const breakfastPricePerNight = 15

const toDateOnly = (date: Date): string => {
  const year = date.getFullYear()
  const month = `${date.getMonth() + 1}`.padStart(2, '0')
  const day = `${date.getDate()}`.padStart(2, '0')
  return `${year}-${month}-${day}`
}

const getMinimumArrivalDate = (): string => {
  const tomorrow = new Date()
  tomorrow.setHours(0, 0, 0, 0)
  tomorrow.setDate(tomorrow.getDate() + 1)
  return toDateOnly(tomorrow)
}

const getQueryValue = (value: unknown): string => {
  if (typeof value === 'string') return value
  if (Array.isArray(value) && typeof value[0] === 'string') return value[0]
  return ''
}

export default defineComponent({
  name: 'BookingView',
  components: {
    PaddedPageTemplate,
    BackButton,
    PeriodSummary,
    BookingSummaryList,
    BookingReviewCard,
    RoomFeatureList,
    RoomInfoPanel,
    IonButton,
    IonIcon,
    IonInput,
    IonSpinner,
    IonToggle,
  },
  setup() {
    return {
      roomStore: useRoomStore(),
      bookingStore: useBookingStore(),
    }
  },
  data() {
    return {
      alertCircleOutline,
      bedOutline,
      callOutline,
      checkmarkCircleOutline,
      locationOutline,
      mailOutline,
      printOutline,
      timeOutline,
      trainOutline,
      roomId: '',
      from: '',
      to: '',
      step: 'form' as BookingStep,
      isConfirmed: false,
      form: {
        firstName: '',
        lastName: '',
        email: '',
        confirmEmail: '',
        breakfast: false,
      } as BookingForm,
      validationErrors: {} as BookingValidationErrors,
    }
  },
  computed: {
    confirmedNightsText(): string {
      const duration = this.bookingStore.currentBooking?.duration
      if (!duration) return this.nightsText
      const label = duration === 1 ? 'Nacht' : 'Nächte'
      return `${duration} ${label}`
    },

    hasValidPeriod(): boolean {
      return Boolean(this.from && this.to && this.from >= getMinimumArrivalDate() && this.to > this.from)
    },

    periodError(): string {
      if (!this.from || !this.to) {
        return 'Bitte prüfen Sie zuerst die Verfügbarkeit für einen Zeitraum.'
      }

      if (this.from < getMinimumArrivalDate()) {
        return 'Die Anreise muss in der Zukunft liegen.'
      }

      if (this.to <= this.from) {
        return 'Die Abreise muss nach der Anreise liegen.'
      }

      return ''
    },

    periodLabel(): string {
      if (!this.from || !this.to) return 'Kein Zeitraum ausgewählt.'
      return `${this.formatDate(this.from)} bis ${this.formatDate(this.to)}`
    },

    guestName(): string {
      return `${this.form.firstName} ${this.form.lastName}`.trim()
    },

    confirmationSummaryItems(): BookingSummaryItem[] {
      const booking = this.bookingStore.currentBooking
      return [
        { label: 'Zimmer', value: this.roomStore.currentRoom?.name ?? booking?.roomId ?? this.roomId },
        { label: 'Zeitraum', value: booking ? `${this.formatDate(booking.from)} bis ${this.formatDate(booking.to)}` : this.periodLabel },
        { label: 'Nächte', value: booking ? String(booking.duration) : this.nightsLabel },
        { label: 'Gesamtpreis', value: booking ? this.formatCurrency(booking.totalPrice) : 'Wird berechnet' },
        { label: 'Frühstück', value: booking?.breakfast ? 'Ja' : 'Nein' },
      ]
    },

    nightsLabel(): string {
      if (!this.hasValidPeriod) return '0'
      const fromDate = new Date(`${this.from}T00:00:00`)
      const toDate = new Date(`${this.to}T00:00:00`)
      const nights = Math.round((toDate.getTime() - fromDate.getTime()) / 86400000)
      return String(nights)
    },

    nightsCount(): number {
      return Number(this.nightsLabel)
    },

    nightsText(): string {
      const label = this.nightsCount === 1 ? 'Nacht' : 'Nächte'
      return `${this.nightsCount} ${label}`
    },

    breakfastDaysText(): string {
      const label = this.nightsCount === 1 ? 'Tag' : 'Tage'
      return `${this.nightsCount} ${label}`
    },

    roomSubtotal(): number {
      return (this.roomStore.currentRoom?.pricePerNight ?? 0) * this.nightsCount
    },

    breakfastSubtotal(): number {
      return this.form.breakfast ? breakfastPricePerNight * this.nightsCount : 0
    },

    estimatedTotal(): number {
      return this.roomSubtotal + this.breakfastSubtotal
    },

    reviewRoomImage(): string {
      return this.roomStore.currentRoom?.imagePaths?.[0] ?? this.roomStore.currentRoom?.coverImagePath ?? ''
    },

    reviewRoomFeatures() {
      const { getIcon } = getIonicIcon()
      return (this.roomStore.currentRoom?.roomExtras ?? []).map((extra) => ({
        label: extra.name,
        icon: getIcon('', extra.icon),
      }))
    },
  },
  watch: {
    '$route.params.id'(newId: string) {
      this.roomId = newId ?? ''
      this.from = getQueryValue(this.$route.query.from)
      this.to = getQueryValue(this.$route.query.to)
      this.roomStore.clearCurrentRoom()
      this.resetState()
      if (this.roomId) {
        this.roomStore.fetchRoomById(this.roomId)
      }
    },
  },
  methods: {
    printBooking(): void {
      window.print()
    },

    resetState(): void {
      this.step = 'form'
      this.isConfirmed = false
      this.form = {
        firstName: '',
        lastName: '',
        email: '',
        confirmEmail: '',
        breakfast: false,
      }
      this.validationErrors = {}
      this.bookingStore.clear()
    },

    goToRoomDetails(): void {
      this.$router.push(`/rooms/${this.roomId}`)
    },

    goToAvailability(): void {
      this.$router.push(`/availability/${this.roomId}`)
    },

    clearFieldError(field: keyof BookingValidationErrors): void {
      if (!this.validationErrors[field]) return
      this.validationErrors = {
        ...this.validationErrors,
        [field]: undefined,
      }
    },

    validateForm(): BookingValidationErrors {
      const errors: BookingValidationErrors = {}

      if (!this.form.firstName) errors.firstName = 'Bitte Vornamen eingeben.'
      if (!this.form.lastName) errors.lastName = 'Bitte Nachnamen eingeben.'

      if (!this.form.email) {
        errors.email = 'Bitte E-Mail-Adresse eingeben.'
      } else if (!emailPattern.test(this.form.email)) {
        errors.email = 'Bitte eine gültige E-Mail-Adresse eingeben.'
      }

      if (!this.form.confirmEmail) {
        errors.confirmEmail = 'Bitte E-Mail-Adresse bestätigen.'
      } else if (this.form.email.toLowerCase() !== this.form.confirmEmail.toLowerCase()) {
        errors.confirmEmail = 'Die E-Mail-Adressen stimmen nicht überein.'
      }

      return errors
    },

    reviewBooking(): void {
      this.validationErrors = this.validateForm()
      this.bookingStore.clear()

      if (Object.keys(this.validationErrors).length > 0 || !this.hasValidPeriod) return

      this.step = 'review'
    },

    async submitBooking(): Promise<void> {
      if (!this.hasValidPeriod || !this.roomId) return

      const booking = await this.bookingStore.createBooking({
        roomId: this.roomId,
        from: this.from,
        to: this.to,
        breakfast: this.form.breakfast,
        userId: null,
        guest: {
          firstName: this.form.firstName,
          lastName: this.form.lastName,
          email: this.form.email,
        },
      })

      if (booking) {
        this.isConfirmed = true
      }
    },

    formatDate(value: string): string {
      return new Intl.DateTimeFormat('de-AT', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
      }).format(new Date(`${value}T00:00:00`))
    },

    formatLongDate(value: string): string {
      if (!value) return 'Kein Datum ausgewählt'

      return new Intl.DateTimeFormat('de-AT', {
        weekday: 'long',
        day: '2-digit',
        month: 'long',
        year: 'numeric',
      }).format(new Date(`${value}T00:00:00`))
    },

    formatCurrency(value: number): string {
      return new Intl.NumberFormat('de-AT', {
        style: 'currency',
        currency: 'EUR',
      }).format(value)
    },
  },
  async mounted(): Promise<void> {
    const routeRoomId = this.$route.params.id
    this.roomId = typeof routeRoomId === 'string' ? routeRoomId : ''
    this.from = getQueryValue(this.$route.query.from)
    this.to = getQueryValue(this.$route.query.to)
    this.roomStore.clearCurrentRoom()
    this.resetState()

    if (this.roomId) {
      await this.roomStore.fetchRoomById(this.roomId)
    }
  },
})
</script>

<style scoped>

.confirmation-layout {
  gap: 24px;
}

.confirmation-hero {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  gap: 14px;
}

.confirmation-icon-wrap {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background: #dcfce7;
}

.confirmation-icon {
  width: 48px;
  height: 48px;
  color: #16a34a;
}

.confirmation-title {
  margin: 0;
  font-size: 1.9rem;
  font-weight: 800;
  color: #1c1917;
}

.confirmation-subtitle {
  margin: 0;
  color: #57534e;
  font-size: 1rem;
  line-height: 1.6;
}

.booking-number-card {
  padding: 22px;
  border: 1px solid #fcd34d;
  border-radius: 10px;
  background: #fffbeb;
  text-align: center;
}

.booking-number-label {
  margin: 0 0 6px;
  color: #78716c;
  font-size: 0.88rem;
}

.booking-number-value {
  margin: 0;
  color: #b45309;
  font-size: 2rem;
  font-weight: 800;
  letter-spacing: 0.03em;
}

.confirmation-section {
  display: grid;
  gap: 16px;
  padding-top: 20px;
  border-top: 1px solid rgba(72, 62, 51, 0.1);
}

.confirmation-section-title {
  margin: 0;
  color: #1c1917;
  font-size: 1rem;
  font-weight: 700;
}

.confirmation-details-list {
  display: grid;
  gap: 10px;
  padding-top: 16px;
  border-top: 1px solid #e2d8cc;
}

.confirmation-details-list p {
  margin: 0;
  color: #57534e;
  line-height: 1.6;
}

.confirmation-details-list strong {
  color: #2e2923;
}

.contact-option-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
}

.contact-option {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 14px;
  border: 1px solid #e2d8cc;
  border-radius: 8px;
  color: #2e2923;
  text-decoration: none;
  transition: background 0.15s;
}

.contact-option:hover {
  background: #f5f0ea;
}

.contact-option ion-icon {
  width: 22px;
  height: 22px;
  color: #b45309;
  flex-shrink: 0;
}

.contact-option span {
  display: flex;
  flex-direction: column;
  gap: 2px;
  font-size: 0.95rem;
}

.contact-option small {
  color: #78716c;
  font-size: 0.78rem;
}

@media (max-width: 480px) {
  .contact-option-grid {
    grid-template-columns: 1fr;
  }
}

.booking-container {
  width: 100%;
  max-width: 1040px;
  margin: 0 auto;
}

.booking-grid {
  display: grid;
  gap: 22px;
}

.booking-panel {
  display: grid;
  gap: 20px;
  padding: 22px;
  border: 1px solid rgba(72, 62, 51, 0.14);
  border-radius: 8px;
  background: #fffdfa;
  box-shadow: 0 14px 34px rgba(52, 39, 25, 0.08);
}

.panel-header,
.confirmation-panel > div {
  display: grid;
  gap: 4px;
}

.panel-header h2,
.confirmation-panel h2 {
  margin: 0;
}

.eyebrow {
  margin: 0;
  color: #2d5b47;
  font-size: 0.8rem;
  font-weight: 900;
  letter-spacing: 0;
  text-transform: uppercase;
}

.period-card {
  display: grid;
  grid-template-columns: 1fr auto;
  align-items: center;
  gap: 12px;
}

.field-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}

.field-group {
  display: grid;
  gap: 8px;
}

.field-label {
  color: #2e2923;
  font-size: 0.92rem;
  font-weight: 700;
}

ion-input {
  min-height: 48px;
  border: 1px solid #d7cfc5;
  border-radius: 6px;
  --padding-start: 14px;
  --padding-end: 14px;
  --background: #ffffff;
  color: #2e2923;
}

.breakfast-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 18px;
  padding: 16px;
  border: 1px solid #e2d8cc;
  border-radius: 8px;
  background: #ffffff;
}

.breakfast-row p,
.confirmation-copy {
  margin: 4px 0 0;
  color: #6b6257;
  line-height: 1.55;
}

ion-toggle {
  flex-shrink: 0;
  --track-background-checked: #b8d1c4;
  --handle-background-checked: #2d5b47;
}

.field-error,
.api-error {
  margin: 0;
  color: #a33a2b;
  font-size: 0.88rem;
}

.panel-actions {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}

.review-card-stack {
  display: grid;
  gap: 20px;
}

.review-room {
  display: grid;
  grid-template-columns: minmax(180px, 0.34fr) 1fr;
  gap: 22px;
}

.review-room-image,
.review-room-fallback {
  width: 100%;
  height: 170px;
  border-radius: 8px;
}

.review-room-image {
  object-fit: cover;
}

.review-room-fallback {
  display: grid;
  place-items: center;
  background: #f5f5f4;
  color: #a8a29e;
  font-size: 2.2rem;
}

.review-room-content {
  display: grid;
  align-content: start;
  gap: 12px;
}

.review-room-content h4 {
  margin: 0;
  color: #1c1917;
  font-size: 1.05rem;
  font-weight: 800;
}

.review-room-content p,
.review-text-list p {
  margin: 0;
  color: #57534e;
  line-height: 1.6;
}

.review-text-list {
  display: grid;
  gap: 10px;
}

.review-text-list strong {
  color: #2e2923;
}

.price-review-card {
  border-color: #f0d9ad;
  background: #fffbeb;
  box-shadow: none;
}

.price-summary {
  display: grid;
  gap: 12px;
}

.price-row {
  display: flex;
  justify-content: space-between;
  gap: 18px;
  color: #57534e;
  line-height: 1.5;
}

.price-row span:last-child {
  color: #2e2923;
  font-weight: 800;
  white-space: nowrap;
}

.price-row.total {
  margin-top: 4px;
  padding-top: 14px;
  border-top: 1px solid #e8c982;
  color: #1c1917;
  font-size: 1.08rem;
  font-weight: 900;
}

.confirmation-icon {
  width: 52px;
  height: 52px;
  color: #16a34a;
}

@media (max-width: 720px) {
  .field-grid,
  .period-card,
  .panel-actions,
  .review-room {
    grid-template-columns: 1fr;
  }

  .period-card ion-button {
    justify-self: start;
  }
}

.map-wrap {
  border-radius: 8px;
  overflow: hidden;
  border: 1px solid #e2d8cc;
}

.map-frame {
  width: 100%;
  height: 260px;
  border: none;
  display: block;
}

@media print {
  .map-frame {
    display: none;
  }
}
</style>
