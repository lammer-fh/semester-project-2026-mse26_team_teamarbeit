export interface BookingGuest {
  firstName: string
  lastName: string
  email: string
}

export interface CreateBookingRequest {
  roomId: string
  from: string
  to: string
  breakfast: boolean
  userId: string | null
  guest: BookingGuest | null
}

export interface Booking {
  id: string
  userId: string
  roomId: string
  from: string
  to: string
  breakfast: boolean
  duration: number
  totalPrice: number
}

export interface BookingListResponse {
  bookings: Booking[]
  page: {
    size: number
    totalElements: number
    totalPages: number
    number: number
  }
}
