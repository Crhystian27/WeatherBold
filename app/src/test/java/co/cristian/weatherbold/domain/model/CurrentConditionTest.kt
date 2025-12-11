package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.base.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CurrentConditionTest {
    
    @Test
    fun `CurrentCondition displayTemp formats correctly`() {
        val condition = TestData.createCurrentCondition(tempCelsius = 12.5)
        
        assertThat(condition.displayTemp).isEqualTo("12°C")
    }
    
    @Test
    fun `CurrentCondition displayFeelsLike formats correctly`() {
        val condition = TestData.createCurrentCondition(feelsLikeCelsius = 9.7)
        
        assertThat(condition.displayFeelsLike).isEqualTo("Sensación: 9°C")
    }
    
    @Test
    fun `CurrentCondition displayWind formats correctly`() {
        val condition = TestData.createCurrentCondition(
            windKph = 15.3,
            windDirection = "NW"
        )
        
        assertThat(condition.displayWind).isEqualTo("Viento: 15 km/h NW")
    }
    
    @Test
    fun `CurrentCondition displayHumidity formats correctly`() {
        val condition = TestData.createCurrentCondition(humidity = 85)
        
        assertThat(condition.displayHumidity).isEqualTo("Humedad: 85%")
    }
    
    @Test
    fun `CurrentCondition displayVisibility formats correctly`() {
        val condition = TestData.createCurrentCondition(visibilityKm = 10.5)
        
        assertThat(condition.displayVisibility).isEqualTo("Visibilidad: 10 km")
    }
    
    @Test
    fun `CurrentCondition contains all weather details`() {
        val condition = TestData.createCurrentCondition(
            conditionText = "Partly Cloudy",
            conditionIcon = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
            tempCelsius = 12.0,
            feelsLikeCelsius = 9.0,
            windKph = 4.0,
            windDirection = "ESE",
            humidity = 100,
            visibilityKm = 10.0
        )
        
        assertThat(condition.conditionText).isEqualTo("Partly Cloudy")
        assertThat(condition.conditionIcon).contains("116.png")
        assertThat(condition.tempCelsius).isEqualTo(12.0)
        assertThat(condition.feelsLikeCelsius).isEqualTo(9.0)
        assertThat(condition.windKph).isEqualTo(4.0)
        assertThat(condition.windDirection).isEqualTo("ESE")
        assertThat(condition.humidity).isEqualTo(100)
        assertThat(condition.visibilityKm).isEqualTo(10.0)
    }
}
