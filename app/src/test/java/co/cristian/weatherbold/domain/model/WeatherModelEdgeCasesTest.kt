package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.base.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Tests para casos edge de los modelos de dominio
 */
class WeatherModelEdgeCasesTest {
    
    @Test
    fun `Location with negative coordinates`() {
        val location = TestData.createLocation(
            latitude = -34.6037,
            longitude = -58.3816
        )
        
        assertThat(location.latitude).isLessThan(0.0)
        assertThat(location.longitude).isLessThan(0.0)
    }
    
    @Test
    fun `CurrentWeather with extreme temperatures`() {
        val hot = TestData.createCurrentWeather(tempCelsius = 50.0)
        val cold = TestData.createCurrentWeather(tempCelsius = -40.0)
        
        assertThat(hot.tempCelsius).isEqualTo(50.0)
        assertThat(cold.tempCelsius).isEqualTo(-40.0)
    }
    
    @Test
    fun `CurrentWeather with zero values`() {
        val weather = TestData.createCurrentWeather(
            tempCelsius = 0.0,
            windKph = 0.0,
            precipMm = 0.0
        )
        
        assertThat(weather.tempCelsius).isEqualTo(0.0)
        assertThat(weather.windKph).isEqualTo(0.0)
        assertThat(weather.precipMm).isEqualTo(0.0)
    }
    
    @Test
    fun `ForecastDay with 100 percent chances`() {
        val forecast = TestData.createForecastDay(
            chanceOfRain = 100,
            chanceOfSnow = 0
        )
        
        assertThat(forecast.chanceOfRain).isEqualTo(100)
        assertThat(forecast.chanceOfSnow).isEqualTo(0)
    }
    
    @Test
    fun `ForecastDay with zero percent chances`() {
        val forecast = TestData.createForecastDay(
            chanceOfRain = 0,
            chanceOfSnow = 0
        )
        
        assertThat(forecast.chanceOfRain).isEqualTo(0)
        assertThat(forecast.chanceOfSnow).isEqualTo(0)
    }
    
    @Test
    fun `HourlyWeather with maximum humidity`() {
        val hourly = HourlyWeather(
            time = "2025-12-11 10:00",
            timeEpoch = 1702296000,
            tempCelsius = 12.0,
            tempFahrenheit = 53.6,
            condition = TestData.createWeatherCondition(),
            windKph = 4.0,
            humidity = 100,
            feelslikeCelsius = 9.0,
            chanceOfRain = 20,
            chanceOfSnow = 0,
            isDay = true
        )
        
        assertThat(hourly.humidity).isEqualTo(100)
    }
    
    @Test
    fun `HourlyWeather with minimum humidity`() {
        val hourly = HourlyWeather(
            time = "2025-12-11 14:00",
            timeEpoch = 1702310400,
            tempCelsius = 25.0,
            tempFahrenheit = 77.0,
            condition = TestData.createWeatherCondition(),
            windKph = 10.0,
            humidity = 0,
            feelslikeCelsius = 24.0,
            chanceOfRain = 0,
            chanceOfSnow = 0,
            isDay = true
        )
        
        assertThat(hourly.humidity).isEqualTo(0)
    }
    
    @Test
    fun `WeatherSummary with single day forecast`() {
        val summary = TestData.createWeatherSummary(
            forecastDays = listOf(TestData.createDayForecast())
        )
        
        assertThat(summary.forecastDays).hasSize(1)
    }
    
    @Test
    fun `WeatherDetail with three day forecast`() {
        val detail = TestData.createWeatherDetail()
        
        assertThat(detail.threeDayForecast).hasSize(3)
        // Day names are now empty and filled in UI with localized resources
        assertThat(detail.threeDayForecast[0].dayName).isEmpty()
        assertThat(detail.threeDayForecast[1].dayName).isEmpty()
        assertThat(detail.threeDayForecast[2].dayName).isEmpty()
    }
    
    @Test
    fun `LocationInfo with different timezones`() {
        val bogota = TestData.createLocationInfo(timezoneId = "America/Bogota")
        val tokyo = TestData.createLocationInfo(timezoneId = "Asia/Tokyo")
        val london = TestData.createLocationInfo(timezoneId = "Europe/London")
        
        assertThat(bogota.timezoneId).isEqualTo("America/Bogota")
        assertThat(tokyo.timezoneId).isEqualTo("Asia/Tokyo")
        assertThat(london.timezoneId).isEqualTo("Europe/London")
    }
}
