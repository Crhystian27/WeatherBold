package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.base.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class WeatherDetailTest {
    
    @Test
    fun `current condition display temp formats correctly`() {
        val condition = TestData.createCurrentCondition(tempCelsius = 12.5)
        
        assertThat(condition.displayTemp).isEqualTo("12°C")
    }
    
    @Test
    fun `current condition display feels like formats correctly`() {
        val condition = TestData.createCurrentCondition(feelsLikeCelsius = 9.3)
        
        assertThat(condition.displayFeelsLike).isEqualTo("Sensación: 9°C")
    }
    
    @Test
    fun `current condition display wind formats correctly`() {
        val condition = TestData.createCurrentCondition(windKph = 4.7, windDirection = "ESE")
        
        assertThat(condition.displayWind).isEqualTo("Viento: 4 km/h ESE")
    }
    
    @Test
    fun `current condition display humidity formats correctly`() {
        val condition = TestData.createCurrentCondition(humidity = 85)
        
        assertThat(condition.displayHumidity).isEqualTo("Humedad: 85%")
    }
    
    @Test
    fun `current condition display visibility formats correctly`() {
        val condition = TestData.createCurrentCondition(visibilityKm = 10.2)
        
        assertThat(condition.displayVisibility).isEqualTo("Visibilidad: 10 km")
    }
    
    @Test
    fun `day forecast display temp formats correctly`() {
        val forecast = TestData.createDayForecastWithName(avgTempCelsius = 15.8)
        
        assertThat(forecast.displayTemp).isEqualTo("15°C")
    }
}
