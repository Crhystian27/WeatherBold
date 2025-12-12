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
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L
    
    // Search Config - Real-time search with instant results
    const val MIN_SEARCH_QUERY_LENGTH = 1  // Show results from first character
    const val SEARCH_DEBOUNCE_DELAY = 400L  // 400ms delay to reduce API calls
    
    // Forecast
    const val DEFAULT_FORECAST_DAYS = 3
}
