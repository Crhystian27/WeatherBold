package co.cristian.weatherbold.data.mapper

import co.cristian.weatherbold.base.TestDtoData
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class WeatherMapperTest {
    
    private lateinit var mapper: WeatherMapper
    
    @Before
    fun setup() {
        mapper = WeatherMapper()
    }
    
    @Test
    fun `map location dto to domain`() {
        val dto = TestDtoData.createLocationDto()
        
        val result = mapper.mapLocationDtoToDomain(dto)
        
        assertThat(result.id).isEqualTo(1)
        assertThat(result.name).isEqualTo("Bogota")
        assertThat(result.region).isEqualTo("Bogota D.C.")
        assertThat(result.country).isEqualTo("Colombia")
        assertThat(result.latitude).isEqualTo(4.61)
        assertThat(result.longitude).isEqualTo(-74.08)
    }
    
    @Test
    fun `map location dto list to domain`() {
        val dtoList = listOf(
            TestDtoData.createLocationDto(1, "Bogota"),
            TestDtoData.createLocationDto(2, "Medellin"),
            TestDtoData.createLocationDto(3, "Cali")
        )
        
        val result = mapper.mapLocationDtoListToDomain(dtoList)
        
        assertThat(result).hasSize(3)
        assertThat(result[0].name).isEqualTo("Bogota")
        assertThat(result[1].name).isEqualTo("Medellin")
        assertThat(result[2].name).isEqualTo("Cali")
    }
    
    @Test
    fun `map forecast response to summary`() {
        val dto = TestDtoData.createForecastResponseDto()
        
        val result = mapper.mapForecastResponseToSummary(dto)
        
        assertThat(result.locationName).isEqualTo("Bogota, Colombia")
        assertThat(result.forecastDays).hasSize(3)
        assertThat(result.forecastDays[0].date).isEqualTo("2025-12-11")
        assertThat(result.forecastDays[0].avgTempCelsius).isEqualTo(12.0)
    }
    
    @Test
    fun `map forecast response to detail`() {
        val dto = TestDtoData.createForecastResponseDto()
        
        val result = mapper.mapForecastResponseToDetail(dto)
        
        assertThat(result.locationName).isEqualTo("Bogota, Colombia")
        assertThat(result.currentWeather.tempCelsius).isEqualTo(12.0)
        assertThat(result.currentWeather.humidity).isEqualTo(100)
        assertThat(result.threeDayForecast).hasSize(3)
        assertThat(result.threeDayForecast[0].dayName).isEqualTo("Hoy")
        assertThat(result.threeDayForecast[1].dayName).isEqualTo("Mañana")
        assertThat(result.threeDayForecast[2].dayName).isEqualTo("Pasado mañana")
    }
    
    @Test
    fun `map forecast response adds https to icon urls`() {
        val dto = TestDtoData.createForecastResponseDto()
        
        val result = mapper.mapForecastResponseToDetail(dto)
        
        assertThat(result.currentWeather.conditionIcon).startsWith("https:")
        assertThat(result.threeDayForecast[0].conditionIcon).startsWith("https:")
    }
    
    @Test
    fun `map forecast response to domain`() {
        val dto = TestDtoData.createForecastResponseDto()
        
        val result = mapper.mapForecastResponseToDomain(dto)
        
        assertThat(result.location.name).isEqualTo("Bogota")
        assertThat(result.current.tempCelsius).isEqualTo(12.0)
        assertThat(result.forecast).hasSize(3)
        assertThat(result.forecast[0].date).isEqualTo("2025-12-11")
    }
}
