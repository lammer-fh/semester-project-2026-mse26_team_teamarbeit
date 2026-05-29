<template>
  <ion-page>
    <ion-header :translucent="true">
      TODO: insert header component here
    </ion-header>

    <ion-content :fullscreen="true" class="availability-content">
      <main class="availability-shell">
        <section class="intro-band">
          <h1>Verfügbarkeit prüfen</h1>
        </section>

        <section class="availability-grid" aria-label="Availability check">
          <room-info-panel
              :room="roomStore.currentRoom"
              :is-loading="roomStore.isLoading"
          />

          <form class="check-panel" @submit.prevent="submitAvailabilityCheck">
            <mode-tabs
                :model-value="periodMode"
                :modes="periodModes"
                @update:model-value="setPeriodMode"
            />

            <div v-if="periodMode === 'exact'" class="period-fields">
              <div class="field-group">
                <label class="field-label" for="arrival-date">Anreise</label>
                <ion-input
                  id="arrival-date"
                  v-model="exactPeriod.from"
                  type="date"
                  :min="minimumArrival"
                  @ionInput="clearAvailability"
                />
                <p v-if="validationErrors.from" class="field-error">{{ validationErrors.from }}</p>
              </div>

              <div class="field-group">
                <label class="field-label" for="departure-date">Abreise</label>
                <ion-input
                  id="departure-date"
                  v-model="exactPeriod.to"
                  type="date"
                  :min="exactPeriod.from || minimumArrival"
                  @ionInput="clearAvailability"
                />
                <p v-if="validationErrors.to" class="field-error">{{ validationErrors.to }}</p>
              </div>
            </div>

            <div v-if="periodMode === 'preset'" class="preset-list">
              <button
                v-for="preset in presets"
                :key="preset.id"
                type="button"
                class="preset-option"
                :class="{ active: selectedPresetId === preset.id }"
                @click="selectPreset(preset.id)"
              >
                <span>{{ preset.label }}</span>
                <small>{{ preset.description }}</small>
                <strong>{{ formatDate(preset.period.from) }} - {{ formatDate(preset.period.to) }}</strong>
              </button>
            </div>

            <div v-if="periodMode === 'nights'" class="period-fields">
              <div class="field-group">
                <label class="field-label" for="nights-arrival">Anreise</label>
                <ion-input
                  id="nights-arrival"
                  v-model="nightsArrival"
                  type="date"
                  :min="minimumArrival"
                  @ionInput="clearAvailability"
                />
                <p v-if="validationErrors.from" class="field-error">{{ validationErrors.from }}</p>
              </div>

              <div class="field-group">
                <label class="field-label" for="night-count">Nächte</label>
                <div class="stepper" id="night-count">
                  <ion-button
                    type="button"
                    fill="clear"
                    aria-label="Nächte verringern"
                    :disabled="nights <= 1"
                    @click="changeNights(-1)"
                  >
                    -
                  </ion-button>
                  <span>{{ nights }}</span>
                  <ion-button
                    type="button"
                    fill="clear"
                    aria-label="Nächte erhöhen"
                    :disabled="nights >= 21"
                    @click="changeNights(1)"
                  >
                    +
                  </ion-button>
                </div>
                <p v-if="validationErrors.to" class="field-error">{{ validationErrors.to }}</p>
              </div>
            </div>

            <period-summary :label="selectedPeriodLabel" />

            <ion-button
              type="submit"
              expand="block"
              size="large"
              :disabled="roomStore.isLoading || !roomId || !roomStore.currentRoom"
            >
              <ion-spinner v-if="roomStore.isLoading" name="crescent" />
              <span v-else>Verfügbarkeit prüfen</span>
            </ion-button>

            <p v-if="roomStore.error" class="api-error">{{ roomStore.error }}</p>

            <availability-result
              v-if="roomStore.isAvailable && lastCheckedPeriod"
              :available="roomStore.isAvailable"
              :period="lastCheckedPeriod"/>
          </form>
        </section>
      </main>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import {
  IonButton,
  IonContent,
  IonHeader,
  IonInput,
  IonPage,
  IonSpinner,
} from '@ionic/vue'

import ModeTabs, { type ModeTabOption, type ModeTabValue } from '@/components/atoms/ModeTabs.vue'
import PeriodSummary from '@/components/molecules/PeriodSummary.vue'
import RoomInfoPanel from '@/components/organisms/RoomInfoPanel.vue'
import { useRoomStore } from '@/stores/room.store'
import AvailabilityResult from "@/components/molecules/AvailabilityResult.vue";

type PeriodInputMode = ModeTabValue

interface TravelPeriod {
  from: string
  to: string
}

interface PeriodPreset {
  id: string
  label: string
  description: string
  period: TravelPeriod
}

type ValidationErrors = Partial<Record<keyof TravelPeriod, string>>

const toDateOnly = (date: Date): string => {
  const year = date.getFullYear()
  const month = `${date.getMonth() + 1}`.padStart(2, '0')
  const day = `${date.getDate()}`.padStart(2, '0')

  return `${year}-${month}-${day}`
}

const addDays = (date: Date, days: number): Date => {
  const next = new Date(date)
  next.setDate(next.getDate() + days)
  return next
}

const getMinimumArrivalDate = (): string => {
  const tomorrow = new Date()
  tomorrow.setHours(0, 0, 0, 0)
  tomorrow.setDate(tomorrow.getDate() + 1)

  return toDateOnly(tomorrow)
}

const createPeriodFromNights = (arrivalDate: string, nights: number): TravelPeriod => {
  if (!arrivalDate) {
    return { from: '', to: '' }
  }

  return {
    from: arrivalDate,
    to: toDateOnly(addDays(new Date(`${arrivalDate}T00:00:00`), nights)),
  }
}

const createPeriodPresets = (): PeriodPreset[] => {
  const start = new Date(`${getMinimumArrivalDate()}T00:00:00`)

  return [
    {
      id: 'weekend',
      label: 'Wochenende',
      description: 'Drei Nächte fuer einen kurzen Aufenthalt.',
      period: createPeriodFromNights(toDateOnly(start), 3),
    },
    {
      id: 'midweek',
      label: 'Unter der Woche',
      description: 'Drei ruhigere Nächte ab Mitte der Woche.',
      period: createPeriodFromNights(toDateOnly(addDays(start, 2)), 3),
    },
    {
      id: 'week',
      label: 'Eine Woche',
      description: 'Sieben Nächte ab nächster Woche.',
      period: createPeriodFromNights(toDateOnly(addDays(start, 7)), 7),
    },
  ]
}

const validateTravelPeriod = (period: TravelPeriod): ValidationErrors => {
  const errors: ValidationErrors = {}
  const minimumArrival = getMinimumArrivalDate()

  if (!period.from) {
    errors.from = 'Bitte Anreisedatum auswählen.'
  } else if (period.from < minimumArrival) {
    errors.from = 'Die Anreise muss in der Zukunft liegen.'
  }

  if (!period.to) {
    errors.to = 'Bitte Abreisedatum auswählen.'
  } else if (period.from && period.to <= period.from) {
    errors.to = 'Die Abreise muss nach der Anreise liegen.'
  }

  return errors
}

export default defineComponent({
  name: 'AvailabilityCheck',
  components: {
    ModeTabs,
    AvailabilityResult,
    PeriodSummary,
    RoomInfoPanel,
    IonButton,
    IonContent,
    IonHeader,
    IonInput,
    IonPage,
    IonSpinner,
  },
  setup() {
    return {
      roomStore: useRoomStore(),
    }
  },
  data() {
    const minimumArrival = getMinimumArrivalDate()
    const presets = createPeriodPresets()

    return {
      roomId: '',
      minimumArrival,
      presets,
      periodModes: [
        { value: 'exact', label: 'Datum' },
        { value: 'preset', label: 'Vorschlag' },
        { value: 'nights', label: 'Nächte' },
      ] as ModeTabOption[],
      periodMode: 'exact' as PeriodInputMode,
      selectedPresetId: presets[0]?.id ?? '',
      nightsArrival: minimumArrival,
      nights: 2,
      exactPeriod: {
        from: minimumArrival,
        to: createPeriodFromNights(minimumArrival, 2).to,
      } as TravelPeriod,
      validationErrors: {} as ValidationErrors,
      lastCheckedPeriod: null as TravelPeriod | null
    }
  },
  computed: {
    selectedPreset(): PeriodPreset {
      return this.presets.find((preset) => preset.id === this.selectedPresetId) ?? this.presets[0]!
    },

    selectedPeriod(): TravelPeriod {
      if (this.periodMode === 'preset') {
        return this.selectedPreset.period
      }

      if (this.periodMode === 'nights') {
        return createPeriodFromNights(this.nightsArrival, this.nights)
      }

      return { from: this.exactPeriod.from, to: this.exactPeriod.to }
    },

    selectedPeriodLabel(): string {
      if (!this.selectedPeriod.from || !this.selectedPeriod.to) {
        return 'Bitte Zeitraum auswählen.'
      }

      return `${this.formatDate(this.selectedPeriod.from)} bis ${this.formatDate(this.selectedPeriod.to)}`
    },
  },

  methods: {
    setPeriodMode(mode: PeriodInputMode): void {
      this.periodMode = mode
      this.validationErrors = {}
      this.clearAvailability()
    },

    selectPreset(id: string): void {
      this.selectedPresetId = id
      this.clearAvailability()
    },

    changeNights(delta: number): void {
      this.nights = Math.min(21, Math.max(1, this.nights + delta))
      this.clearAvailability()
    },

    clearAvailability(): void {
      this.roomStore.clearAvailability()
      this.lastCheckedPeriod = null
    },

    async submitAvailabilityCheck(): Promise<void> {
      this.validationErrors = validateTravelPeriod(this.selectedPeriod)

      if (Object.keys(this.validationErrors).length > 0 || !this.roomId || !this.roomStore.currentRoom) {
        this.clearAvailability()
        return
      }

      const period = { ...this.selectedPeriod }
      this.lastCheckedPeriod = null
      this.roomStore.clearAvailability()
      await this.roomStore.checkAvailability(this.roomId, period)
      this.lastCheckedPeriod = this.roomStore.isAvailable === null ? null : period
    },

    formatDate(value: string): string {
      return new Intl.DateTimeFormat('de-AT', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
      }).format(new Date(`${value}T00:00:00`))
    },
  },

  async mounted(): Promise<void> {
    const routeRoomId = this.$route.params.id
    this.roomId = typeof routeRoomId === 'string' ? routeRoomId : ''
    this.roomStore.clearCurrentRoom()

    if (this.roomId) {
      await this.roomStore.fetchRoomById(this.roomId)
    }
  },
})
</script>

<style scoped>
.availability-content {
  --background: #f7f4ef;
}

.availability-shell {
  width: min(1040px, calc(100% - 32px));
  margin: 0 auto;
  padding: 40px 0 56px;
}

.intro-band {
  margin-bottom: 22px;
}

h1,
h2,
h3,
p {
  margin-top: 0;
}

h1 {
  margin-bottom: 0;
  color: #25211d;
  font-size: clamp(2rem, 4vw, 3.2rem);
  line-height: 1.08;
}

.availability-grid {
  display: grid;
  gap: 22px;
}

.check-panel {
  border: 1px solid rgba(72, 62, 51, 0.14);
  border-radius: 8px;
  background: #fffdfa;
  box-shadow: 0 14px 34px rgba(52, 39, 25, 0.08);
}

.check-panel {
  display: grid;
  gap: 20px;
  padding: 22px;
}

.image-fallback ion-icon {
  font-size: 2.2rem;
}

.room-error ion-icon {
  font-size: 2.2rem;
}

.room-error h2 {
  margin-bottom: 0;
  color: #25211d;
}

.room-error p {
  margin-bottom: 0;
  color: #62584d;
}

.room-preview h2 {
  margin-bottom: 6px;
  color: #25211d;
  font-size: 1.12rem;
}

.room-preview p {
  margin-bottom: 8px;
  color: #62584d;
  font-size: 0.92rem;
  line-height: 1.45;
}

.room-preview strong {
  color: #2d5b47;
  font-size: 0.95rem;
}

.period-fields {
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

.field-error,
.api-error {
  margin: 0;
  color: #a33a2b;
  font-size: 0.88rem;
}

.preset-list {
  display: grid;
  gap: 10px;
}

.preset-option {
  display: grid;
  gap: 4px;
  width: 100%;
  padding: 14px;
  border: 1px solid #d9d0c3;
  border-radius: 8px;
  background: #ffffff;
  color: #2d2823;
  text-align: left;
}

.preset-option.active {
  border-color: #2d5b47;
  background: #f1f7f4;
}

.preset-option span {
  font-weight: 800;
}

.preset-option small {
  color: #6b6257;
  line-height: 1.45;
}

.preset-option strong {
  color: #2d5b47;
  font-size: 0.92rem;
}

.stepper {
  display: grid;
  grid-template-columns: 48px 1fr 48px;
  align-items: center;
  min-height: 48px;
  border: 1px solid #d7cfc5;
  border-radius: 6px;
  background: #ffffff;
}

.stepper span {
  color: #2e2923;
  font-size: 1.2rem;
  font-weight: 800;
  text-align: center;
}

.period-summary ion-icon {
  font-size: 1.25rem;
}

ion-button[type='submit'] {
  --background: #2d5b47;
  --background-hover: #244a3a;
  --border-radius: 6px;
  --box-shadow: none;
  min-height: 50px;
  font-weight: 800;
}

.availability-result {
  display: grid;
  grid-template-columns: auto 1fr;
  gap: 14px;
  padding: 16px;
  border-radius: 8px;
}

.availability-result ion-icon {
  font-size: 2rem;
}

.availability-result h3 {
  margin-bottom: 4px;
  font-size: 1.05rem;
}

.availability-result p {
  margin: 0;
}

.availability-result.available {
  border: 1px solid #b9dec9;
  background: #eef9f2;
  color: #28603b;
}

.availability-result.unavailable {
  border: 1px solid #efc0b7;
  background: #fff1ee;
  color: #943525;
}

@media (max-width: 820px) {
  .period-fields {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 560px) {
  .availability-shell {
    width: min(100% - 20px, 1040px);
    padding-top: 24px;
  }
}
</style>
