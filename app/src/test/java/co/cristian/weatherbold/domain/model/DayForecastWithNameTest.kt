package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.base.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DayForecastWithNameTest {
    
    @Test
    fun `DayForecastWithName displayTemp formats correctly`() {
        val forecast = TestData.createDayForecastWithName(avgTempCelsius = 15.7)
        
        assertThat(forecast.displayTemp).isEqualTo("15°C")
    }
    
    @Test
    fun `DayForecastWithName contains day name`() {
        val today = TestData.createDayForecastWithName(dayName = "Hoy")
        val tomorrow = TestData.createDayForecastWithName(dayName = "Mañana")
        val dayAfter = TestData.createDayForecastWithName(dayName = "Pasado mañana")
        
        assertThat(today.dayName).isEqualTo("Hoy")
        assertThat(tomorrow.dayName).isEqualTo("Mañana")
        assertThat(dayAfter.dayName).isEqualTo("Pasado mañana")
    }
    
    @Test
    fun `DayForecastWithName contains date`() {
        val forecast = TestData.createDayForecastWithName(date = "2025-12-15")
        
        assertThat(forecast.date).isEqualTo("2025-12-15")
    }
    
    @Test
    fun `DayForecastWithName contains condition`() {
        val forecast = TestData.createDayForecastWithName(
            conditionText = "Sunny",
            conditionIcon = "https://cdn.weatherapi.com/weather/64x64/day/113.png"
        )
        
        assertThat(forecast.conditionText).isEqualTo("Sunny")
        assertThat(forecast.conditionIcon).contains("113.png")
    }
    
    @Test
    fun `DayForecastWithName contains temperature`() {
        val forecast = TestData.createDayForecastWithName(avgTempCelsius = 18.5)
        
        assertThat(forecast.avgTempCelsius).isEqualTo(18.5)
    }
}
