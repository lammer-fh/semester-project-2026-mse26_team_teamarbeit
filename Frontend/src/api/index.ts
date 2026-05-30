import axios from 'axios'

const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080',
    headers: { 'Content-Type': 'application/json' },
})

api.interceptors.response.use(
    (response) => response,
    (error) => {
        const message = error.response?.data?.errorMessage ?? 'Ein unerwarteter Fehler ist aufgetreten!'
        return Promise.reject(new Error(message))
    }
)

export default api