import api from '@/api'
import type { Booking, CreateBookingRequest } from '@/models/booking.model'

export const BookingApi = {
  create: (payload: CreateBookingRequest): Promise<Booking> =>
    api.post<Booking>('/bookings', payload).then((response) => response.data),
}
