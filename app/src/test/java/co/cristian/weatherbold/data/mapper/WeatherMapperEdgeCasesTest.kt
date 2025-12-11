package co.cristian.weatherbold.data.mapper

import co.cristian.weatherbold.base.TestDtoData
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Tests adicionales para WeatherMapper
 * Enfocados en casos edge y transformaciones
 */
class WeatherMapperEdgeCasesTest {
    
    private lateinit var mapper: WeatherMapper
    
    @Before
    fun setup() {
        mapper = WeatherMapper()
    }
    
    @Test
    fun `mapLocationDtoListToDomain with empty list`() {
        val result = mapper.mapLocationDtoListToDomain(emptyList())
        
        assertThat(result).isEmpty()
    }
    
    @Test
    fun `mapLocationDtoListToDomain with single item`() {
        val dtoList = listOf(TestDtoData.createLocationDto())
        
        val result = mapper.mapLocationDtoListToDomain(dtoList)
        
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("Bogota")
    }
    
    @Test
    fun `mapLocationDtoToDomain preserves all fields`() {
        val dto = TestDtoData.createLocationDto(
            id = 123,
            name = "Test City",
            region = "Test Region",
            country = "Test Country",
            latitude = 12.34,
            longitude = 56.78
        )
        
        val result = mapper.mapLocationDtoToDomain(dto)
        
        assertThat(result.id).isEqualTo(123)
        assertThat(result.name).isEqualTo("Test City")
        assertThat(result.region).isEqualTo("Test Region")
        assertThat(result.country).isEqualTo("Test Country")
        assertThat(result.latitude).isEqualTo(12.34)
        assertThat(result.longitude).isEqualTo(56.78)
    }
    
    @Test
    fun `mapForecastResponseToSummary creates correct location name`() {
        val dto = TestDtoData.createForecastResponseDto()
        
        val result = mapper.mapForecastResponseToSummary(dto)
        
        assertThat(result.locationName).contains("Bogota")
        assertThat(result.locationName).contains("Colombia")
    }
    
    @Test
    fun `mapForecastResponseToDetail creates correct location name`() {
        val dto = TestDtoData.createForecastResponseDto()
        
        val result = mapper.mapForecastResponseToDetail(dto)
        
        assertThat(result.locationName).contains("Bogota")
        assertThat(result.locationName).contains("Colombia")
    }
    
    @Test
    fun `mapForecastResponseToDetail maps current weather correctly`() {
        val dto = TestDtoData.createForecastResponseDto()
        
        val result = mapper.mapForecastResponseToDetail(dto)
        
        assertThat(result.currentWeather.tempCelsius).isEqualTo(12.0)
        assertThat(result.currentWeather.humidity).isEqualTo(100)
        assertThat(result.currentWeather.windKph).isEqualTo(4.0)
    }
    
    @Test
    fun `mapForecastResponseToDetail assigns correct day names`() {
        val dto = TestDtoData.createForecastResponseDto()
        
        val result = mapper.mapForecastResponseToDetail(dto)
        
        assertThat(result.threeDayForecast).hasSize(3)
        // Day names are now empty and filled in UI with localized resources
        assertThat(result.threeDayForecast[0].dayName).isEmpty()
        assertThat(result.threeDayForecast[1].dayName).isEmpty()
        assertThat(result.threeDayForecast[2].dayName).isEmpty()
    }
    
    @Test
    fun `mapForecastResponseToDomain maps location info`() {
        val dto = TestDtoData.createForecastResponseDto()
        
        val result = mapper.mapForecastResponseToDomain(dto)
        
        assertThat(result.location.name).isEqualTo("Bogota")
        assertThat(result.location.country).isEqualTo("Colombia")
    }
    
    @Test
    fun `mapForecastResponseToDomain maps forecast days`() {
        val dto = TestDtoData.createForecastResponseDto()
        
        val result = mapper.mapForecastResponseToDomain(dto)
        
        assertThat(result.forecast).hasSize(3)
        assertThat(result.forecast[0].date).isEqualTo("2025-12-11")
    }
    
    @Test
    fun `mapForecastResponseToSummary adds https to icon urls`() {
        val dto = TestDtoData.createForecastResponseDto()
        
        val result = mapper.mapForecastResponseToSummary(dto)
        
        assertThat(result.forecastDays[0].conditionIcon).startsWith("https:")
    }
}
