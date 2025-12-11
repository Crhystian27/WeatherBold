package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.base.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class WeatherSummaryTest {
    
    @Test
    fun `WeatherSummary contains location and forecast days`() {
        val summary = TestData.createWeatherSummary()
        
        assertThat(summary.locationName).isEqualTo("Bogota, Colombia")
        assertThat(summary.forecastDays).hasSize(3)
    }
    
    @Test
    fun `DayForecast contains essential forecast info`() {
        val dayForecast = TestData.createDayForecast()
        
        assertThat(dayForecast.date).isEqualTo("2025-12-11")
        assertThat(dayForecast.conditionText).isEqualTo("Partly Cloudy")
        assertThat(dayForecast.avgTempCelsius).isEqualTo(12.0)
    }
    
    @Test
    fun `DayForecast displayDate returns formatted date`() {
        val dayForecast = TestData.createDayForecast(date = "2025-12-11")
        
        assertThat(dayForecast.displayDate).isEqualTo("2025-12-11")
    }
    
    @Test
    fun `DayForecast displayTemp returns formatted temperature`() {
        val dayForecast = TestData.createDayForecast(avgTempCelsius = 12.5)
        
        assertThat(dayForecast.displayTemp).isEqualTo("12°C")
    }
}
