package co.cristian.weatherbold.domain.model

/**
 * Extension functions for display formatting
 * 
 * Purpose: Backward compatibility with existing tests
 * Usage: Tests use these extensions for simple formatting
 * 
 * Note: Production code should use WeatherFormatter for localized formatting
 */

// CurrentCondition display extensions
val CurrentCondition.displayTemp: String
    get() = "${tempCelsius.toInt()}°C"

val CurrentCondition.displayFeelsLike: String
    get() = "Sensación: ${feelsLikeCelsius.toInt()}°C"

val CurrentCondition.displayWind: String
    get() = "Viento: ${windKph.toInt()} km/h $windDirection"

val CurrentCondition.displayHumidity: String
    get() = "Humedad: $humidity%"

val CurrentCondition.displayVisibility: String
    get() = "Visibilidad: ${visibilityKm.toInt()} km"

// DayForecastWithName display extensions
val DayForecastWithName.displayTemp: String
    get() = "${avgTempCelsius.toInt()}°C"

// DayForecast display extensions
val DayForecast.displayTemp: String
    get() = "${avgTempCelsius.toInt()}°C"

val DayForecast.displayDate: String
    get() = date
