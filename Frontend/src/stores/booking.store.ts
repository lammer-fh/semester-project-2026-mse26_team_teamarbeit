import { ref } from 'vue'
import { defineStore } from 'pinia'
import { BookingApi } from '@/api/endpoints/booking.endpoints'
import type { Booking, CreateBookingRequest } from '@/models/booking.model'

export const useBookingStore = defineStore('booking', () => {
  const currentBooking = ref<Booking | null>(null)
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  const createBooking = async (payload: CreateBookingRequest): Promise<Booking | null> => {
    isLoading.value = true
    error.value = null

    try {
      currentBooking.value = await BookingApi.create(payload)
      return currentBooking.value
    } catch (e) {
      error.value = (e as Error).message
      return null
    } finally {
      isLoading.value = false
    }
  }

  const clear = (): void => {
    currentBooking.value = null
    error.value = null
  }

  return {
    currentBooking,
    isLoading,
    error,
    createBooking,
    clear,
  }
})
