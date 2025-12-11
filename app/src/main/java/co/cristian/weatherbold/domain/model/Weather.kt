package co.cristian.weatherbold.domain.model

data class Weather(
    val location: LocationInfo,
    val current: CurrentWeather,
    val forecast: List<ForecastDay>
)

data class LocationInfo(
    val name: String,
    val region: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val localtime: String,
    val timezoneId: String
)

data class CurrentWeather(
    val tempCelsius: Double,
    val tempFahrenheit: Double,
    val condition: WeatherCondition,
    val windKph: Double,
    val windDirection: String,
    val humidity: Int,
    val feelslikeCelsius: Double,
    val feelslikeFahrenheit: Double,
    val uvIndex: Double,
    val visibilityKm: Double,
    val precipMm: Double,
    val pressureMb: Double,
    val cloud: Int,
    val isDay: Boolean,
    val lastUpdated: String
)

data class WeatherCondition(
    val text: String,
    val icon: String,
    val code: Int
)

data class ForecastDay(
    val date: String,
    val dateEpoch: Long,
    val maxTempCelsius: Double,
    val minTempCelsius: Double,
    val avgTempCelsius: Double,
    val maxTempFahrenheit: Double,
    val minTempFahrenheit: Double,
    val avgTempFahrenheit: Double,
    val condition: WeatherCondition,
    val maxWindKph: Double,
    val totalPrecipMm: Double,
    val avgHumidity: Double,
    val chanceOfRain: Int,
    val chanceOfSnow: Int,
    val uvIndex: Double,
    val sunrise: String,
    val sunset: String,
    val moonPhase: String,
    val hourlyForecast: List<HourlyWeather>
)

data class HourlyWeather(
    val time: String,
    val timeEpoch: Long,
    val tempCelsius: Double,
    val tempFahrenheit: Double,
    val condition: WeatherCondition,
    val windKph: Double,
    val humidity: Int,
    val feelslikeCelsius: Double,
    val chanceOfRain: Int,
    val chanceOfSnow: Int,
    val isDay: Boolean
)
