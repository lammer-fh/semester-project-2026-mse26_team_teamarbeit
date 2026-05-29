import type { PageMeta, PaginationParams } from './shared.model'

export interface Room {
    id: string
    name: string
    description: string
    pricePerNight: number
    extras: string[]
    imagePaths: string[]
}

// Request objects
export interface RoomListParams extends PaginationParams {
    filter?: string
    sort?:   string
    page:    number
}

// date format: ISO-Date YYYY-MM-DD
export interface RoomAvailabilityParams {
    from: string
    to:   string
}

// Response objects
export interface RoomListResponse {
    rooms: Room[]
    page:  PageMeta
}

export interface RoomAvailabilityResponse {
    available: boolean
}
