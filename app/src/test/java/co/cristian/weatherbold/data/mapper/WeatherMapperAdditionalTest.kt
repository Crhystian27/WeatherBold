package co.cristian.weatherbold.data.mapper

import co.cristian.weatherbold.base.TestDtoData
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class WeatherMapperAdditionalTest {

    private lateinit var mapper: WeatherMapper

    @Before
    fun setup() {
        mapper = WeatherMapper()
    }

    @Test
    fun `mapForecastResponseToDomain maps location info correctly`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDomain(dto)

        assertThat(result.location.name).isEqualTo("Bogota")
        assertThat(result.location.region).isEqualTo("Bogota D.C.")
        assertThat(result.location.country).isEqualTo("Colombia")
        assertThat(result.location.latitude).isEqualTo(4.61)
        assertThat(result.location.longitude).isEqualTo(-74.08)
    }

    @Test
    fun `mapForecastResponseToDomain maps current weather correctly`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDomain(dto)

        assertThat(result.current.tempCelsius).isEqualTo(12.0)
        assertThat(result.current.humidity).isEqualTo(100)
        assertThat(result.current.windKph).isEqualTo(4.0)
        assertThat(result.current.windDirection).isEqualTo("ESE")
    }

    @Test
    fun `mapForecastResponseToDomain maps forecast days correctly`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDomain(dto)

        assertThat(result.forecast).hasSize(3)
        assertThat(result.forecast[0].date).isEqualTo("2025-12-11")
        assertThat(result.forecast[1].date).isEqualTo("2025-12-12")
        assertThat(result.forecast[2].date).isEqualTo("2025-12-13")
    }

    @Test
    fun `mapForecastResponseToDomain maps day temperatures correctly`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDomain(dto)

        val firstDay = result.forecast[0]
        assertThat(firstDay.maxTempCelsius).isEqualTo(15.0)
        assertThat(firstDay.minTempCelsius).isEqualTo(8.0)
        assertThat(firstDay.avgTempCelsius).isEqualTo(12.0)
    }

    @Test
    fun `mapForecastResponseToDomain maps weather conditions correctly`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDomain(dto)

        assertThat(result.current.condition.text).isEqualTo("Partly Cloudy")
        assertThat(result.current.condition.code).isEqualTo(1003)
    }

    @Test
    fun `mapForecastResponseToDetail maps current condition correctly`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDetail(dto)

        assertThat(result.currentWeather.tempCelsius).isEqualTo(12.0)
        assertThat(result.currentWeather.feelsLikeCelsius).isEqualTo(9.0)
        assertThat(result.currentWeather.humidity).isEqualTo(100)
        assertThat(result.currentWeather.visibilityKm).isEqualTo(10.0)
    }

    @Test
    fun `mapForecastResponseToSummary maps location name correctly`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToSummary(dto)

        assertThat(result.locationName).contains("Bogota")
        assertThat(result.locationName).contains("Colombia")
    }

    @Test
    fun `mapForecastResponseToSummary maps forecast days correctly`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToSummary(dto)

        assertThat(result.forecastDays).hasSize(3)
        assertThat(result.forecastDays[0].avgTempCelsius).isEqualTo(12.0)
    }

    @Test
    fun `mapLocationDtoListToDomain handles empty list`() {
        val result = mapper.mapLocationDtoListToDomain(emptyList())

        assertThat(result).isEmpty()
    }

    @Test
    fun `mapLocationDtoListToDomain handles single item`() {
        val dto = TestDtoData.createLocationDto()
        
        val result = mapper.mapLocationDtoListToDomain(listOf(dto))

        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("Bogota")
    }
}
