<template>
  <PaddedPageTemplate>
    <BackButton :to="`/rooms/${roomId}`">Zurück zum Zimmer</BackButton>

    <div class="availability-container">
      <header>
        <h1>Verfügbarkeit prüfen</h1>
      </header>

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
              :period="lastCheckedPeriod"
              @book="goToBooking"
          />
        </form>
      </section>
    </div>
  </PaddedPageTemplate>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import {
  IonButton,
  IonInput,
  IonSpinner,
} from '@ionic/vue'

import PaddedPageTemplate from '@/components/templates/PaddedPageTemplate.vue'
import BackButton from '@/components/molecules/BackButton.vue'
import ModeTabs, { type ModeTabOption, type ModeTabValue } from '@/components/atoms/ModeTabs.vue'
import PeriodSummary from '@/components/molecules/PeriodSummary.vue'
import RoomInfoPanel from '@/components/organisms/RoomInfoPanel.vue'
import AvailabilityResult from '@/components/molecules/AvailabilityResult.vue'
import { useRoomStore } from '@/stores/room.store'

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
  if (!arrivalDate) return { from: '', to: '' }
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
    PaddedPageTemplate,
    BackButton,
    ModeTabs,
    AvailabilityResult,
    PeriodSummary,
    RoomInfoPanel,
    IonButton,
    IonInput,
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
      lastCheckedPeriod: null as TravelPeriod | null,
    }
  },
  computed: {
    selectedPreset(): PeriodPreset {
      return this.presets.find((preset) => preset.id === this.selectedPresetId) ?? this.presets[0]!
    },

    selectedPeriod(): TravelPeriod {
      if (this.periodMode === 'preset') return this.selectedPreset.period
      if (this.periodMode === 'nights') return createPeriodFromNights(this.nightsArrival, this.nights)
      return { from: this.exactPeriod.from, to: this.exactPeriod.to }
    },

    selectedPeriodLabel(): string {
      if (!this.selectedPeriod.from || !this.selectedPeriod.to) return 'Bitte Zeitraum auswählen.'
      return `${this.formatDate(this.selectedPeriod.from)} bis ${this.formatDate(this.selectedPeriod.to)}`
    },
  },
  methods: {
    goToBooking() {
      this.$router.push(`/rooms/${this.roomId}/book`)
    },

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

      await this.roomStore.checkAvailability(this.roomId, {
        from: period.from,
        to: period.to
      })

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
.availability-container {
  width: 100%;
  max-width: 1040px;
  margin: 0 auto;
}

.availability-grid {
  display: grid;
  gap: 22px;
}

.check-panel {
  display: grid;
  gap: 20px;
  padding: 22px;
  border: 1px solid rgba(72, 62, 51, 0.14);
  border-radius: 8px;
  background: #fffdfa;
  box-shadow: 0 14px 34px rgba(52, 39, 25, 0.08);
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

@media (max-width: 820px) {
  .period-fields {
    grid-template-columns: 1fr;
  }
}
</style>