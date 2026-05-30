export interface PageMeta {
    size:          number
    totalElements: number
    totalPages:    number
    number:        number
}

export interface PaginationParams {
    page?: number
    size?: number
}