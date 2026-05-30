import { ref, computed }  from 'vue'
import { defineStore }    from 'pinia'
import { RoomApi }        from '@/api/endpoints/room.endpoints'
import type { Room, RoomListParams, RoomAvailabilityParams } from '@/models/room.model'
import type { PageMeta }                                      from '@/models/shared.model'
/*import type { Booking }                                      from '@/models/booking.model'*/

export const useRoomStore = defineStore('room', () => {

    // State
    const rooms           = ref<Room[]>([])
    const currentRoom     = ref<Room | null>(null)
    const pagination      = ref<PageMeta | null>(null)
    const isAvailable     = ref<boolean | null>(null)
    const isLoading       = ref(false)
    const error           = ref<string | null>(null)

    const hasNextPage = computed(() =>
        pagination.value
            ? !pagination.value.last
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
            rooms.value      = result.items
            pagination.value = {
                last:        result.last,
                pageNumber:  result.pageNumber,
                pageSize:    result.pageSize,
                totalItems:  result.totalItems,
                totalPages:  result.totalPages,
            }
        })

    const fetchRoomById = (id: string) =>
        withLoading(async () => {
            currentRoom.value = await RoomApi.getById(id)
        })

    const checkAvailability = (id: string, params: RoomAvailabilityParams) =>
        withLoading(async () => {
            const result = await RoomApi.checkAvailability(id, params)
            isAvailable.value = result.available
        })

    const clearCurrentRoom = (): void => {
        currentRoom.value = null
        isAvailable.value = null
    }

    const clearAvailability = (): void => {
        isAvailable.value = null
        error.value = null
    }

    return {
        rooms,
        currentRoom,
        pagination,
        isAvailable,
        isLoading,
        error,
        hasNextPage,
        fetchRooms,
        fetchRoomById,
        checkAvailability,
        clearCurrentRoom,
        clearAvailability,
    }
})