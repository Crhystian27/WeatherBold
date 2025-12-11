package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.base.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ForecastDayTest {
    
    @Test
    fun `ForecastDay contains all temperature fields`() {
        val forecastDay = TestData.createForecastDay()
        
        assertThat(forecastDay.avgTempCelsius).isEqualTo(12.0)
        assertThat(forecastDay.maxTempCelsius).isEqualTo(15.0)
        assertThat(forecastDay.minTempCelsius).isEqualTo(8.0)
        assertThat(forecastDay.avgTempFahrenheit).isEqualTo(53.6)
        assertThat(forecastDay.maxTempFahrenheit).isEqualTo(59.0)
        assertThat(forecastDay.minTempFahrenheit).isEqualTo(46.4)
    }
    
    @Test
    fun `ForecastDay contains weather condition`() {
        val forecastDay = TestData.createForecastDay()
        
        assertThat(forecastDay.condition.text).isEqualTo("Partly Cloudy")
        assertThat(forecastDay.condition.code).isEqualTo(1003)
    }
    
    @Test
    fun `ForecastDay contains precipitation data`() {
        val forecastDay = TestData.createForecastDay(
            totalPrecipMm = 5.5,
            chanceOfRain = 60,
            chanceOfSnow = 0
        )
        
        assertThat(forecastDay.totalPrecipMm).isEqualTo(5.5)
        assertThat(forecastDay.chanceOfRain).isEqualTo(60)
        assertThat(forecastDay.chanceOfSnow).isEqualTo(0)
    }
    
    @Test
    fun `ForecastDay contains wind and humidity data`() {
        val forecastDay = TestData.createForecastDay(
            maxWindKph = 15.0,
            avgHumidity = 75.0
        )
        
        assertThat(forecastDay.maxWindKph).isEqualTo(15.0)
        assertThat(forecastDay.avgHumidity).isEqualTo(75.0)
    }
    
    @Test
    fun `ForecastDay contains astronomical data`() {
        val forecastDay = TestData.createForecastDay(
            sunrise = "06:30 AM",
            sunset = "06:30 PM",
            moonPhase = "Full Moon"
        )
        
        assertThat(forecastDay.sunrise).isEqualTo("06:30 AM")
        assertThat(forecastDay.sunset).isEqualTo("06:30 PM")
        assertThat(forecastDay.moonPhase).isEqualTo("Full Moon")
    }
    
    @Test
    fun `ForecastDay contains UV index`() {
        val forecastDay = TestData.createForecastDay(uvIndex = 5.0)
        
        assertThat(forecastDay.uvIndex).isEqualTo(5.0)
    }
    
    @Test
    fun `ForecastDay contains date information`() {
        val forecastDay = TestData.createForecastDay(
            date = "2025-12-15",
            dateEpoch = 1702598400
        )
        
        assertThat(forecastDay.date).isEqualTo("2025-12-15")
        assertThat(forecastDay.dateEpoch).isEqualTo(1702598400)
    }
    
    @Test
    fun `ForecastDay can have empty hourly forecast`() {
        val forecastDay = TestData.createForecastDay(hourlyForecast = emptyList())
        
        assertThat(forecastDay.hourlyForecast).isEmpty()
    }
}
