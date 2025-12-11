package co.cristian.weatherbold.domain.model


data class WeatherDetail(
    val locationName: String,           // "Bogota, Colombia"
    val currentWeather: CurrentCondition, // Clima actual con detalles
    val threeDayForecast: List<DayForecastWithName> // 3 días de pronóstico
)

data class CurrentCondition(
    val conditionText: String,      // "Partly Cloudy"
    val conditionIcon: String,      // URL del icono
    val tempCelsius: Double,        // 12.0
    val feelsLikeCelsius: Double,   // 9.0
    val windKph: Double,            // 4.0
    val windDirection: String,      // "ESE"
    val humidity: Int,              // 100
    val visibilityKm: Double        // 10.0
) {

    val displayTemp: String
        get() = "${tempCelsius.toInt()}°C"

    val displayFeelsLike: String
        get() = "Sensación: ${feelsLikeCelsius.toInt()}°C"

    val displayWind: String
        get() = "Viento: ${windKph.toInt()} km/h $windDirection"

    val displayHumidity: String
        get() = "Humedad: $humidity%"

    val displayVisibility: String
        get() = "Visibilidad: ${visibilityKm.toInt()} km"
}

data class DayForecastWithName(
    val date: String,              // "2025-12-10"
    val dayName: String,           // "Hoy", "Mañana", "Pasado mañana"
    val conditionText: String,     // "Partly Cloudy"
    val conditionIcon: String,     // URL del icono
    val avgTempCelsius: Double     // 12.0
) {

    val displayTemp: String
        get() = "${avgTempCelsius.toInt()}°C"
}
