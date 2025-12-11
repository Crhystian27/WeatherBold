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
    
    // Search Config
    const val MIN_SEARCH_QUERY_LENGTH = 3
    const val SEARCH_DEBOUNCE_DELAY = 600L
    
    // Forecast
    const val DEFAULT_FORECAST_DAYS = 3
    
    // Country Flags CDN
    object FlagCdn {
        const val BASE_URL = "https://flagcdn.com/"

        object Sizes {
            const val NORMAL = "h80"
        }

        object Formats {
            const val WEBP = "webp"
            const val PNG = "png"
        }

        /**
         * Example: buildFlagUrl("CO", "h80") → "https://flagcdn.com/h80/co.webp"
         * Example: buildFlagUrl("CO", "h80", "png") → "https://flagcdn.com/h80/co.png"
         */
        fun buildFlagUrl(
            isoCode: String, 
            size: String = Sizes.NORMAL,
            format: String = Formats.WEBP
        ): String {
            return "$BASE_URL$size/${isoCode.lowercase()}.$format"
        }
    }
}
