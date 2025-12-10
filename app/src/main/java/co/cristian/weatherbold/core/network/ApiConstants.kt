package co.cristian.weatherbold.core.network

object ApiConstants {

    const val BASE_URL = "https://api.weatherapi.com/"

    object Endpoints {
        const val SEARCH = "v1/search.json"
        const val FORECAST = "v1/forecast.json"
    }

    object QueryParams {
        const val API_KEY = "key"
        const val QUERY = "q"
        const val DAYS = "days"
    }
    
    // Timeouts
    const val CONNECT_TIMEOUT = 30L // segundos
    const val READ_TIMEOUT = 30L // segundos
    const val WRITE_TIMEOUT = 30L // segundos
    
    // Search Config
    const val MIN_SEARCH_QUERY_LENGTH = 3
    const val SEARCH_DEBOUNCE_DELAY = 300L
    
    // Forecast
    const val MAX_FORECAST_DAYS = 7
    const val DEFAULT_FORECAST_DAYS = 3
}
