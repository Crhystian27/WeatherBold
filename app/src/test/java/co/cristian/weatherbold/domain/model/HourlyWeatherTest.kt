package co.cristian.weatherbold.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class HourlyWeatherTest {
    
    @Test
    fun `HourlyWeather contains all required fields`() {
        val hourly = HourlyWeather(
            time = "2025-12-11 10:00",
            timeEpoch = 1702296000,
            tempCelsius = 12.0,
            tempFahrenheit = 53.6,
            condition = WeatherCondition("Partly Cloudy", "//cdn.weatherapi.com/weather/64x64/day/116.png", 1003),
            windKph = 4.0,
            humidity = 100,
            feelslikeCelsius = 9.0,
            chanceOfRain = 20,
            chanceOfSnow = 0,
            isDay = true
        )
        
        assertThat(hourly.time).isEqualTo("2025-12-11 10:00")
        assertThat(hourly.timeEpoch).isEqualTo(1702296000)
        assertThat(hourly.tempCelsius).isEqualTo(12.0)
        assertThat(hourly.tempFahrenheit).isEqualTo(53.6)
        assertThat(hourly.windKph).isEqualTo(4.0)
        assertThat(hourly.humidity).isEqualTo(100)
        assertThat(hourly.feelslikeCelsius).isEqualTo(9.0)
        assertThat(hourly.chanceOfRain).isEqualTo(20)
        assertThat(hourly.chanceOfSnow).isEqualTo(0)
        assertThat(hourly.isDay).isTrue()
    }
    
    @Test
    fun `HourlyWeather condition contains weather info`() {
        val condition = WeatherCondition("Sunny", "//cdn.weatherapi.com/weather/64x64/day/113.png", 1000)
        val hourly = HourlyWeather(
            time = "2025-12-11 12:00",
            timeEpoch = 1702303200,
            tempCelsius = 15.0,
            tempFahrenheit = 59.0,
            condition = condition,
            windKph = 5.0,
            humidity = 80,
            feelslikeCelsius = 14.0,
            chanceOfRain = 10,
            chanceOfSnow = 0,
            isDay = true
        )
        
        assertThat(hourly.condition.text).isEqualTo("Sunny")
        assertThat(hourly.condition.code).isEqualTo(1000)
    }
    
    @Test
    fun `HourlyWeather night time has isDay false`() {
        val hourly = HourlyWeather(
            time = "2025-12-11 22:00",
            timeEpoch = 1702339200,
            tempCelsius = 8.0,
            tempFahrenheit = 46.4,
            condition = WeatherCondition("Clear", "//cdn.weatherapi.com/weather/64x64/night/113.png", 1000),
            windKph = 3.0,
            humidity = 90,
            feelslikeCelsius = 6.0,
            chanceOfRain = 5,
            chanceOfSnow = 0,
            isDay = false
        )
        
        assertThat(hourly.isDay).isFalse()
        assertThat(hourly.tempCelsius).isLessThan(10.0)
    }
    
    @Test
    fun `HourlyWeather with rain chance`() {
        val hourly = HourlyWeather(
            time = "2025-12-11 15:00",
            timeEpoch = 1702314000,
            tempCelsius = 13.0,
            tempFahrenheit = 55.4,
            condition = WeatherCondition("Light Rain", "//cdn.weatherapi.com/weather/64x64/day/296.png", 1183),
            windKph = 8.0,
            humidity = 95,
            feelslikeCelsius = 11.0,
            chanceOfRain = 80,
            chanceOfSnow = 0,
            isDay = true
        )
        
        assertThat(hourly.chanceOfRain).isEqualTo(80)
        assertThat(hourly.chanceOfSnow).isEqualTo(0)
        assertThat(hourly.condition.text).contains("Rain")
    }
    
    @Test
    fun `HourlyWeather with snow chance`() {
        val hourly = HourlyWeather(
            time = "2025-12-11 18:00",
            timeEpoch = 1702324800,
            tempCelsius = -2.0,
            tempFahrenheit = 28.4,
            condition = WeatherCondition("Light Snow", "//cdn.weatherapi.com/weather/64x64/day/326.png", 1210),
            windKph = 10.0,
            humidity = 100,
            feelslikeCelsius = -5.0,
            chanceOfRain = 0,
            chanceOfSnow = 70,
            isDay = true
        )
        
        assertThat(hourly.chanceOfSnow).isEqualTo(70)
        assertThat(hourly.chanceOfRain).isEqualTo(0)
        assertThat(hourly.tempCelsius).isLessThan(0.0)
    }
}
