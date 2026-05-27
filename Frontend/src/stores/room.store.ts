import { ref, computed }  from 'vue'
import { defineStore }    from 'pinia'
import { RoomApi }        from '@/api/endpoints/room.endpoints'
import type { Room, RoomListParams, RoomAvailabilityParams } from '@/models/room.model'
import type { PageMeta, PaginationParams }                   from '@/models/shared.model'
/*import type { Booking }                                      from '@/models/booking.model'*/

export const useRoomStore = defineStore('room', () => {

    // State
    const rooms           = ref<Room[]>([])
    const currentRoom     = ref<Room | null>(null)
    const roomBookings    = ref<Booking[]>([])
    const pagination      = ref<PageMeta | null>(null)
    const isAvailable     = ref<boolean | null>(null)
    const isLoading       = ref(false)
    const error           = ref<string | null>(null)

    const hasNextPage = computed(() =>
        pagination.value
            ? pagination.value.number < pagination.value.totalPages - 1
            : false
    )

    // Helpers
    const withLoading = async (fn: () => Promise<void>): Promise<void> => {
        isLoading.value = true
        error.value     = null
        try {
            await fn()
        } catch (e) {
            error.value = (e as Error).message
        } finally {
            isLoading.value = false
        }
    }

    // Actions
    const fetchRooms = (params: RoomListParams) =>
        withLoading(async () => {
            const result     = await RoomApi.getList(params)
            rooms.value      = result.rooms
            pagination.value = result.page
        })

    const fetchRoomById = (id: string) =>
        withLoading(async () => {
            currentRoom.value = await RoomApi.getById(id)   // ✅ Room, unambiguously
        })

    const checkAvailability = (id: string, params: RoomAvailabilityParams) =>
        withLoading(async () => {
            const result      = await RoomApi.checkAvailability(id, params)
            isAvailable.value = result.available
        })

    /*const fetchRoomBookings = (id: string, params?: PaginationParams) =>
        withLoading(async () => {
            const result       = await RoomApi.getBookings(id, params)
            roomBookings.value = result.bookings
            pagination.value   = result.page
        })*/

    // Reset
    const clearCurrentRoom = (): void => {
        currentRoom.value = null
        isAvailable.value = null
        roomBookings.value = []
    }

    return {
        rooms, currentRoom, roomBookings,
        pagination, isAvailable, isLoading, error,
        hasNextPage,
        fetchRooms, fetchRoomById, checkAvailability,
        // fetchRoomBookings,
        clearCurrentRoom,
    }
})