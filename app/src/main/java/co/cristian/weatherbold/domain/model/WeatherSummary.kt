package co.cristian.weatherbold.domain.model

/**
 * Weather summary with simplified forecast data
 */
data class WeatherSummary(
    val locationName: String,
    val forecastDays: List<DayForecast>
)

/**
 * Daily forecast with basic information
 */
data class DayForecast(
    val date: String,
    val conditionText: String,
    val conditionIcon: String,
    val avgTempCelsius: Double
)
