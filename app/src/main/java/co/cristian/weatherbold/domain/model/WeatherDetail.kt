package co.cristian.weatherbold.domain.model

/**
 * Weather detail for UI display
 * Contains current conditions and 3-day forecast
 */
data class WeatherDetail(
    val locationName: String,
    val currentWeather: CurrentCondition,
    val threeDayForecast: List<DayForecastWithName>
)

/**
 * Current weather condition with essential display data
 */
data class CurrentCondition(
    val conditionText: String,
    val conditionIcon: String,
    val tempCelsius: Double,
    val feelsLikeCelsius: Double,
    val windKph: Double,
    val windDirection: String,
    val humidity: Int,
    val visibilityKm: Double
)

/**
 * Daily forecast with localized day name
 * dayName is filled in UI using WeatherFormatter.getDayName()
 */
data class DayForecastWithName(
    val date: String,
    val dayName: String,
    val conditionText: String,
    val conditionIcon: String,
    val avgTempCelsius: Double
)
