import axios from 'axios'

const apiBaseUrl = 'http://localhost:8080'

const api = axios.create({
    baseURL: apiBaseUrl,
    headers: { 'Content-Type': 'application/json' },
})

api.interceptors.response.use(
    (response) => response,
    (error) => {
        const message = error.response?.data?.errorMessage ?? 'An unexpected error occurred.'
        return Promise.reject(new Error(message))
    }
)

export default api
