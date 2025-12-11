package co.cristian.weatherbold.domain.model

data class WeatherSummary(
    val locationName: String,           // "Bogota, Colombia"
    val forecastDays: List<DayForecast> // Solo 3 días
)

data class DayForecast(
    val date: String,              // "2025-12-10"
    val conditionText: String,     // "Partly Cloudy"
    val conditionIcon: String,     // "https://cdn.weatherapi.com/weather/64x64/day/116.png"
    val avgTempCelsius: Double     // 12.0
) {
    /**
     * Fecha formateada para mostrar en la UI
     * Puedes personalizarlo según tu necesidad
     */
    //TODO revisar
    val displayDate: String
        get() = date // Por ahora retorna la fecha tal cual, puedes formatearla después
    
    /**
     * Temperatura formateada para mostrar
     */
    //TODO Revisar
    val displayTemp: String
        get() = "${avgTempCelsius.toInt()}°C"
}
