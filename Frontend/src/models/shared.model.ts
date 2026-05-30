export interface PageMeta {
    last:        boolean
    pageNumber:  number
    pageSize:    number
    totalItems:  number
    totalPages:  number
}

export interface PaginationParams {
    page?: number
    size?: number
}