export interface RoomExtra {
    name: string
    icon: string
}

export interface Room {
    id: string
    name: string
    description: string
    pricePerNight: number
    coverImagePath: string | null
    imagePaths: string[]
    roomExtras: RoomExtra[]
}

export interface RoomListResponse {
    items: Room[]
    last: boolean
    pageNumber: number
    pageSize: number
    totalItems: number
    totalPages: number
}

export interface RoomAvailabilityResponse {
    available: boolean
}

export interface RoomListParams {
    page?: number;
    size?: number;
}

export interface RoomAvailabilityParams {
    from: string;
    to: string;
}