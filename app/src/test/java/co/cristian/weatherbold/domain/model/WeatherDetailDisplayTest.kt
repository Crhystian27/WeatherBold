package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.base.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Tests enfocados en propiedades computadas de WeatherDetail
 */
class WeatherDetailDisplayTest {
    
    @Test
    fun `CurrentCondition displayTemp rounds temperature correctly`() {
        val condition1 = TestData.createCurrentCondition(tempCelsius = 12.1)
        val condition2 = TestData.createCurrentCondition(tempCelsius = 12.5)
        val condition3 = TestData.createCurrentCondition(tempCelsius = 12.9)
        
        assertThat(condition1.displayTemp).isEqualTo("12°C")
        assertThat(condition2.displayTemp).isEqualTo("12°C")
        assertThat(condition3.displayTemp).isEqualTo("12°C")
    }
    
    @Test
    fun `CurrentCondition displayFeelsLike rounds correctly`() {
        val condition1 = TestData.createCurrentCondition(feelsLikeCelsius = 9.1)
        val condition2 = TestData.createCurrentCondition(feelsLikeCelsius = 9.9)
        
        assertThat(condition1.displayFeelsLike).isEqualTo("Sensación: 9°C")
        assertThat(condition2.displayFeelsLike).isEqualTo("Sensación: 9°C")
    }
    
    @Test
    fun `CurrentCondition displayWind formats with different speeds`() {
        val slow = TestData.createCurrentCondition(windKph = 5.3, windDirection = "N")
        val medium = TestData.createCurrentCondition(windKph = 15.7, windDirection = "NE")
        val fast = TestData.createCurrentCondition(windKph = 45.9, windDirection = "SW")
        
        assertThat(slow.displayWind).isEqualTo("Viento: 5 km/h N")
        assertThat(medium.displayWind).isEqualTo("Viento: 15 km/h NE")
        assertThat(fast.displayWind).isEqualTo("Viento: 45 km/h SW")
    }
    
    @Test
    fun `CurrentCondition displayHumidity formats percentage`() {
        val low = TestData.createCurrentCondition(humidity = 30)
        val medium = TestData.createCurrentCondition(humidity = 65)
        val high = TestData.createCurrentCondition(humidity = 95)
        
        assertThat(low.displayHumidity).isEqualTo("Humedad: 30%")
        assertThat(medium.displayHumidity).isEqualTo("Humedad: 65%")
        assertThat(high.displayHumidity).isEqualTo("Humedad: 95%")
    }
    
    @Test
    fun `CurrentCondition displayVisibility rounds correctly`() {
        val condition1 = TestData.createCurrentCondition(visibilityKm = 10.1)
        val condition2 = TestData.createCurrentCondition(visibilityKm = 10.9)
        val condition3 = TestData.createCurrentCondition(visibilityKm = 5.5)
        
        assertThat(condition1.displayVisibility).isEqualTo("Visibilidad: 10 km")
        assertThat(condition2.displayVisibility).isEqualTo("Visibilidad: 10 km")
        assertThat(condition3.displayVisibility).isEqualTo("Visibilidad: 5 km")
    }
    
    @Test
    fun `DayForecastWithName displayTemp rounds correctly`() {
        val forecast1 = TestData.createDayForecastWithName(avgTempCelsius = 18.1)
        val forecast2 = TestData.createDayForecastWithName(avgTempCelsius = 18.9)
        val forecast3 = TestData.createDayForecastWithName(avgTempCelsius = -2.5)
        
        assertThat(forecast1.displayTemp).isEqualTo("18°C")
        assertThat(forecast2.displayTemp).isEqualTo("18°C")
        assertThat(forecast3.displayTemp).isEqualTo("-2°C")
    }
    
    @Test
    fun `DayForecast displayTemp rounds correctly`() {
        val forecast1 = TestData.createDayForecast(avgTempCelsius = 22.3)
        val forecast2 = TestData.createDayForecast(avgTempCelsius = 22.7)
        
        assertThat(forecast1.displayTemp).isEqualTo("22°C")
        assertThat(forecast2.displayTemp).isEqualTo("22°C")
    }
    
    @Test
    fun `DayForecast displayDate returns date string`() {
        val forecast1 = TestData.createDayForecast(date = "2025-12-15")
        val forecast2 = TestData.createDayForecast(date = "2025-01-01")
        
        assertThat(forecast1.displayDate).isEqualTo("2025-12-15")
        assertThat(forecast2.displayDate).isEqualTo("2025-01-01")
    }
}
