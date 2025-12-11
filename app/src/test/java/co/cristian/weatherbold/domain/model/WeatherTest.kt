package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.base.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class WeatherTest {
    
    @Test
    fun `Weather model holds correct data`() {
        val weather = TestData.createWeather()
        
        assertThat(weather.location.name).isEqualTo("Bogota")
        assertThat(weather.current.tempCelsius).isEqualTo(12.0)
        assertThat(weather.forecast).hasSize(3)
    }
    
    @Test
    fun `LocationInfo contains all required fields`() {
        val locationInfo = TestData.createLocationInfo()
        
        assertThat(locationInfo.name).isEqualTo("Bogota")
        assertThat(locationInfo.region).isEqualTo("Bogota D.C.")
        assertThat(locationInfo.country).isEqualTo("Colombia")
        assertThat(locationInfo.latitude).isEqualTo(4.61)
        assertThat(locationInfo.longitude).isEqualTo(-74.08)
    }
    
    @Test
    fun `CurrentWeather contains all weather details`() {
        val current = TestData.createCurrentWeather()
        
        assertThat(current.tempCelsius).isEqualTo(12.0)
        assertThat(current.feelslikeCelsius).isEqualTo(9.0)
        assertThat(current.windKph).isEqualTo(4.0)
        assertThat(current.humidity).isEqualTo(100)
        assertThat(current.visibilityKm).isEqualTo(10.0)
    }
    
    @Test
    fun `WeatherCondition contains text icon and code`() {
        val condition = TestData.createWeatherCondition()
        
        assertThat(condition.text).isEqualTo("Partly Cloudy")
        assertThat(condition.icon).contains("116.png")
        assertThat(condition.code).isEqualTo(1003)
    }
    
    @Test
    fun `ForecastDay contains complete forecast data`() {
        val forecastDay = TestData.createForecastDay()
        
        assertThat(forecastDay.date).isEqualTo("2025-12-11")
        assertThat(forecastDay.avgTempCelsius).isEqualTo(12.0)
        assertThat(forecastDay.maxTempCelsius).isEqualTo(15.0)
        assertThat(forecastDay.minTempCelsius).isEqualTo(8.0)
        assertThat(forecastDay.chanceOfRain).isEqualTo(20)
    }
}
