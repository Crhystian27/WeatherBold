package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.base.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CurrentWeatherTest {
    
    @Test
    fun `CurrentWeather contains temperature data`() {
        val current = TestData.createCurrentWeather()
        
        assertThat(current.tempCelsius).isEqualTo(12.0)
        assertThat(current.tempFahrenheit).isEqualTo(53.6)
        assertThat(current.feelslikeCelsius).isEqualTo(9.0)
        assertThat(current.feelslikeFahrenheit).isEqualTo(48.2)
    }
    
    @Test
    fun `CurrentWeather contains wind data`() {
        val current = TestData.createCurrentWeather(
            windKph = 15.0,
            windDirection = "NW"
        )
        
        assertThat(current.windKph).isEqualTo(15.0)
        assertThat(current.windDirection).isEqualTo("NW")
    }
    
    @Test
    fun `CurrentWeather contains atmospheric data`() {
        val current = TestData.createCurrentWeather(
            humidity = 85,
            pressureMb = 1015.0,
            cloud = 50
        )
        
        assertThat(current.humidity).isEqualTo(85)
        assertThat(current.pressureMb).isEqualTo(1015.0)
        assertThat(current.cloud).isEqualTo(50)
    }
    
    @Test
    fun `CurrentWeather contains visibility and precipitation`() {
        val current = TestData.createCurrentWeather(
            visibilityKm = 8.0,
            precipMm = 2.5
        )
        
        assertThat(current.visibilityKm).isEqualTo(8.0)
        assertThat(current.precipMm).isEqualTo(2.5)
    }
    
    @Test
    fun `CurrentWeather contains UV index`() {
        val current = TestData.createCurrentWeather(uvIndex = 7.0)
        
        assertThat(current.uvIndex).isEqualTo(7.0)
    }
    
    @Test
    fun `CurrentWeather contains day night indicator`() {
        val dayWeather = TestData.createCurrentWeather(isDay = true)
        val nightWeather = TestData.createCurrentWeather(isDay = false)
        
        assertThat(dayWeather.isDay).isTrue()
        assertThat(nightWeather.isDay).isFalse()
    }
    
    @Test
    fun `CurrentWeather contains condition`() {
        val condition = WeatherCondition("Sunny", "//cdn.weatherapi.com/weather/64x64/day/113.png", 1000)
        val current = TestData.createCurrentWeather(condition = condition)
        
        assertThat(current.condition.text).isEqualTo("Sunny")
        assertThat(current.condition.code).isEqualTo(1000)
    }
    
    @Test
    fun `CurrentWeather contains last updated timestamp`() {
        val current = TestData.createCurrentWeather(lastUpdated = "2025-12-11 15:30")
        
        assertThat(current.lastUpdated).isEqualTo("2025-12-11 15:30")
    }
}
