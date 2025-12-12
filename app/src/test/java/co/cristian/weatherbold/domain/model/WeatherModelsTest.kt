package co.cristian.weatherbold.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class WeatherModelsTest {

    @Test
    fun `LocationInfo creates correctly`() {
        val locationInfo = LocationInfo(
            name = "Bogota",
            region = "Bogota D.C.",
            country = "Colombia",
            latitude = 4.61,
            longitude = -74.08,
            localtime = "2025-12-11 10:00",
            timezoneId = "America/Bogota"
        )

        assertThat(locationInfo.name).isEqualTo("Bogota")
        assertThat(locationInfo.latitude).isEqualTo(4.61)
    }

    @Test
    fun `CurrentWeather creates correctly`() {
        val condition = WeatherCondition("Sunny", "icon.png", 1000)
        val currentWeather = CurrentWeather(
            tempCelsius = 25.0,
            tempFahrenheit = 77.0,
            condition = condition,
            windKph = 10.0,
            windDirection = "N",
            humidity = 60,
            feelslikeCelsius = 24.0,
            feelslikeFahrenheit = 75.2,
            uvIndex = 5.0,
            visibilityKm = 10.0,
            precipMm = 0.0,
            pressureMb = 1013.0,
            cloud = 20,
            isDay = true,
            lastUpdated = "2025-12-11 10:00"
        )

        assertThat(currentWeather.tempCelsius).isEqualTo(25.0)
        assertThat(currentWeather.isDay).isTrue()
    }

    @Test
    fun `WeatherCondition creates correctly`() {
        val condition = WeatherCondition(
            text = "Partly Cloudy",
            icon = "https://icon.png",
            code = 1003
        )

        assertThat(condition.text).isEqualTo("Partly Cloudy")
        assertThat(condition.code).isEqualTo(1003)
    }

    @Test
    fun `ForecastDay creates correctly`() {
        val condition = WeatherCondition("Sunny", "icon.png", 1000)
        val forecastDay = ForecastDay(
            date = "2025-12-11",
            dateEpoch = 1702252800,
            maxTempCelsius = 28.0,
            minTempCelsius = 18.0,
            avgTempCelsius = 23.0,
            maxTempFahrenheit = 82.4,
            minTempFahrenheit = 64.4,
            avgTempFahrenheit = 73.4,
            condition = condition,
            maxWindKph = 15.0,
            totalPrecipMm = 0.0,
            avgHumidity = 65.0,
            chanceOfRain = 10,
            chanceOfSnow = 0,
            uvIndex = 6.0,
            sunrise = "06:00 AM",
            sunset = "06:00 PM",
            moonPhase = "Full Moon",
            hourlyForecast = emptyList()
        )

        assertThat(forecastDay.date).isEqualTo("2025-12-11")
        assertThat(forecastDay.maxTempCelsius).isEqualTo(28.0)
    }

    @Test
    fun `HourlyWeather creates correctly`() {
        val condition = WeatherCondition("Clear", "icon.png", 1000)
        val hourlyWeather = HourlyWeather(
            time = "2025-12-11 10:00",
            timeEpoch = 1702296000,
            tempCelsius = 22.0,
            tempFahrenheit = 71.6,
            condition = condition,
            windKph = 8.0,
            humidity = 70,
            feelslikeCelsius = 21.0,
            chanceOfRain = 5,
            chanceOfSnow = 0,
            isDay = true
        )

        assertThat(hourlyWeather.tempCelsius).isEqualTo(22.0)
        assertThat(hourlyWeather.isDay).isTrue()
    }

    @Test
    fun `Weather creates correctly`() {
        val locationInfo = LocationInfo(
            "Bogota", "Bogota D.C.", "Colombia", 4.61, -74.08, "2025-12-11 10:00", "America/Bogota"
        )
        val condition = WeatherCondition("Sunny", "icon.png", 1000)
        val currentWeather = CurrentWeather(
            25.0, 77.0, condition, 10.0, "N", 60, 24.0, 75.2, 5.0, 10.0, 0.0, 1013.0, 20, true, "2025-12-11 10:00"
        )
        val weather = Weather(
            location = locationInfo,
            current = currentWeather,
            forecast = emptyList()
        )

        assertThat(weather.location.name).isEqualTo("Bogota")
        assertThat(weather.current.tempCelsius).isEqualTo(25.0)
    }

    @Test
    fun `WeatherDetail creates correctly`() {
        val currentCondition = CurrentCondition(
            conditionText = "Sunny",
            conditionIcon = "icon.png",
            tempCelsius = 25.0,
            feelsLikeCelsius = 24.0,
            windKph = 10.0,
            windDirection = "N",
            humidity = 60,
            visibilityKm = 10.0
        )
        val forecast = DayForecastWithName(
            date = "2025-12-11",
            dayName = "Hoy",
            conditionText = "Sunny",
            conditionIcon = "icon.png",
            avgTempCelsius = 23.0
        )
        val weatherDetail = WeatherDetail(
            locationName = "Bogota, Colombia",
            currentWeather = currentCondition,
            threeDayForecast = listOf(forecast)
        )

        assertThat(weatherDetail.locationName).isEqualTo("Bogota, Colombia")
        assertThat(weatherDetail.threeDayForecast).hasSize(1)
    }

    @Test
    fun `WeatherSummary creates correctly`() {
        val dayForecast = DayForecast(
            date = "2025-12-11",
            conditionText = "Sunny",
            conditionIcon = "icon.png",
            avgTempCelsius = 23.0
        )
        val weatherSummary = WeatherSummary(
            locationName = "Bogota, Colombia",
            forecastDays = listOf(dayForecast)
        )

        assertThat(weatherSummary.locationName).isEqualTo("Bogota, Colombia")
        assertThat(weatherSummary.forecastDays).hasSize(1)
    }
}
