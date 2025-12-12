package co.cristian.weatherbold.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class WeatherDisplayExtensionsTest {

    @Test
    fun `CurrentCondition displayTemp formats correctly`() {
        val condition = CurrentCondition(
            conditionText = "Sunny",
            conditionIcon = "icon.png",
            tempCelsius = 25.7,
            feelsLikeCelsius = 23.2,
            windKph = 15.5,
            windDirection = "N",
            humidity = 65,
            visibilityKm = 10.0
        )

        assertThat(condition.displayTemp).isEqualTo("25°C")
    }

    @Test
    fun `CurrentCondition displayFeelsLike formats correctly`() {
        val condition = CurrentCondition(
            conditionText = "Cloudy",
            conditionIcon = "icon.png",
            tempCelsius = 20.0,
            feelsLikeCelsius = 18.8,
            windKph = 10.0,
            windDirection = "S",
            humidity = 70,
            visibilityKm = 8.0
        )

        assertThat(condition.displayFeelsLike).isEqualTo("Sensación: 18°C")
    }

    @Test
    fun `CurrentCondition displayWind formats correctly`() {
        val condition = CurrentCondition(
            conditionText = "Windy",
            conditionIcon = "icon.png",
            tempCelsius = 22.0,
            feelsLikeCelsius = 20.0,
            windKph = 25.3,
            windDirection = "NE",
            humidity = 60,
            visibilityKm = 12.0
        )

        assertThat(condition.displayWind).isEqualTo("Viento: 25 km/h NE")
    }

    @Test
    fun `CurrentCondition displayHumidity formats correctly`() {
        val condition = CurrentCondition(
            conditionText = "Humid",
            conditionIcon = "icon.png",
            tempCelsius = 28.0,
            feelsLikeCelsius = 30.0,
            windKph = 5.0,
            windDirection = "W",
            humidity = 85,
            visibilityKm = 6.0
        )

        assertThat(condition.displayHumidity).isEqualTo("Humedad: 85%")
    }

    @Test
    fun `CurrentCondition displayVisibility formats correctly`() {
        val condition = CurrentCondition(
            conditionText = "Clear",
            conditionIcon = "icon.png",
            tempCelsius = 24.0,
            feelsLikeCelsius = 24.0,
            windKph = 8.0,
            windDirection = "E",
            humidity = 55,
            visibilityKm = 15.7
        )

        assertThat(condition.displayVisibility).isEqualTo("Visibilidad: 15 km")
    }

    @Test
    fun `DayForecastWithName displayTemp formats correctly`() {
        val forecast = DayForecastWithName(
            date = "2025-12-11",
            dayName = "Hoy",
            conditionText = "Sunny",
            conditionIcon = "icon.png",
            avgTempCelsius = 23.6
        )

        assertThat(forecast.displayTemp).isEqualTo("23°C")
    }

    @Test
    fun `DayForecast displayTemp formats correctly`() {
        val forecast = DayForecast(
            date = "2025-12-12",
            conditionText = "Rainy",
            conditionIcon = "icon.png",
            avgTempCelsius = 18.9
        )

        assertThat(forecast.displayTemp).isEqualTo("18°C")
    }

    @Test
    fun `DayForecast displayDate returns date`() {
        val forecast = DayForecast(
            date = "2025-12-13",
            conditionText = "Cloudy",
            conditionIcon = "icon.png",
            avgTempCelsius = 20.0
        )

        assertThat(forecast.displayDate).isEqualTo("2025-12-13")
    }

    @Test
    fun `displayTemp rounds down correctly`() {
        val condition = CurrentCondition(
            conditionText = "Test",
            conditionIcon = "icon.png",
            tempCelsius = 25.4,
            feelsLikeCelsius = 24.0,
            windKph = 10.0,
            windDirection = "N",
            humidity = 60,
            visibilityKm = 10.0
        )

        assertThat(condition.displayTemp).isEqualTo("25°C")
    }

    @Test
    fun `displayTemp rounds up correctly`() {
        val condition = CurrentCondition(
            conditionText = "Test",
            conditionIcon = "icon.png",
            tempCelsius = 25.6,
            feelsLikeCelsius = 24.0,
            windKph = 10.0,
            windDirection = "N",
            humidity = 60,
            visibilityKm = 10.0
        )

        assertThat(condition.displayTemp).isEqualTo("25°C")
    }
}
