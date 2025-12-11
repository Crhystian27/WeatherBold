package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.base.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class WeatherConditionTest {
    
    @Test
    fun `WeatherCondition contains text icon and code`() {
        val condition = TestData.createWeatherCondition()
        
        assertThat(condition.text).isEqualTo("Partly Cloudy")
        assertThat(condition.icon).contains("116.png")
        assertThat(condition.code).isEqualTo(1003)
    }
    
    @Test
    fun `WeatherCondition for sunny weather`() {
        val condition = WeatherCondition(
            text = "Sunny",
            icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
            code = 1000
        )
        
        assertThat(condition.text).isEqualTo("Sunny")
        assertThat(condition.code).isEqualTo(1000)
    }
    
    @Test
    fun `WeatherCondition for rainy weather`() {
        val condition = WeatherCondition(
            text = "Moderate rain",
            icon = "//cdn.weatherapi.com/weather/64x64/day/302.png",
            code = 1189
        )
        
        assertThat(condition.text).contains("rain")
        assertThat(condition.code).isEqualTo(1189)
    }
    
    @Test
    fun `WeatherCondition for cloudy weather`() {
        val condition = WeatherCondition(
            text = "Overcast",
            icon = "//cdn.weatherapi.com/weather/64x64/day/122.png",
            code = 1009
        )
        
        assertThat(condition.text).isEqualTo("Overcast")
        assertThat(condition.code).isEqualTo(1009)
    }
    
    @Test
    fun `WeatherCondition icon contains path`() {
        val condition = TestData.createWeatherCondition()
        
        assertThat(condition.icon).contains("cdn.weatherapi.com")
        assertThat(condition.icon).contains(".png")
    }
}
