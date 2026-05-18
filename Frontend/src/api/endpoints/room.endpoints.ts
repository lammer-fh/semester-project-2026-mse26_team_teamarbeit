import api from '@/api'
import type {
    Room,
    RoomListParams,
    RoomListResponse,
    RoomAvailabilityParams,
    RoomAvailabilityResponse,
} from '@/models/room.model'
import type { PaginationParams } from '@/models/shared.model'
/*
import type { BookingListResponse } from '@/models/booking.model'
*/

export const RoomApi = {

    getList: (params: RoomListParams): Promise<RoomListResponse> =>
        api.get<RoomListResponse>('/rooms', { params }).then(r => r.data),

    getById: (id: string): Promise<Room> =>
        api.get<Room>(`/rooms/${id}`).then(r => r.data),

    checkAvailability: (id: string, params: RoomAvailabilityParams): Promise<RoomAvailabilityResponse> =>
        api.get<RoomAvailabilityResponse>(`/rooms/${id}/availability`, { params }).then(r => r.data),

    /*getBookings: (id: string, params?: PaginationParams): Promise<BookingListResponse> =>
        api.get<BookingListResponse>(`/rooms/${id}/bookings`, { params }).then(r => r.data),*/

}