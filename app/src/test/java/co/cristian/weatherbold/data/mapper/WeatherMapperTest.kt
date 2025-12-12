package co.cristian.weatherbold.data.mapper

import co.cristian.weatherbold.base.TestDtoData
import co.cristian.weatherbold.data.remote.dto.ForecastDataDto
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
    fun `mapLocationDtoToDomain maps correctly`() {
        val dto = TestDtoData.createLocationDto()

        val result = mapper.mapLocationDtoToDomain(dto)

        assertThat(result.id).isEqualTo(dto.id)
        assertThat(result.name).isEqualTo(dto.name)
        assertThat(result.region).isEqualTo(dto.region)
        assertThat(result.country).isEqualTo(dto.country)
        assertThat(result.latitude).isEqualTo(dto.latitude)
        assertThat(result.longitude).isEqualTo(dto.longitude)
    }

    @Test
    fun `mapLocationDtoListToDomain maps list correctly`() {
        val dtoList = TestDtoData.createLocationDtoList()

        val result = mapper.mapLocationDtoListToDomain(dtoList)

        assertThat(result).hasSize(3)
        assertThat(result[0].name).isEqualTo("Bogota")
        assertThat(result[1].name).isEqualTo("Medellin")
        assertThat(result[2].name).isEqualTo("Cali")
    }

    @Test
    fun `mapForecastResponseToDomain maps all fields correctly`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDomain(dto)

        assertThat(result.location.name).isEqualTo("Bogota")
        assertThat(result.current.tempCelsius).isEqualTo(12.0)
        assertThat(result.forecast).hasSize(3)
    }

    @Test
    fun `mapForecastResponseToDomain adds https protocol to icon`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDomain(dto)

        assertThat(result.current.condition.icon).startsWith("https://")
    }

    @Test
    fun `mapForecastResponseToDetail creates correct structure`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDetail(dto)

        assertThat(result.locationName).contains("Bogota")
        assertThat(result.locationName).contains("Colombia")
        assertThat(result.currentWeather).isNotNull()
        assertThat(result.threeDayForecast).hasSize(3)
    }

    @Test
    fun `mapForecastResponseToDetail sets empty dayName`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDetail(dto)

        result.threeDayForecast.forEach { day ->
            assertThat(day.dayName).isEmpty()
        }
    }

    @Test
    fun `mapForecastResponseToDetail adds https to icons`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDetail(dto)

        assertThat(result.currentWeather.conditionIcon).startsWith("https://")
        result.threeDayForecast.forEach { day ->
            assertThat(day.conditionIcon).startsWith("https://")
        }
    }

    @Test
    fun `mapForecastResponseToSummary takes only 3 days`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToSummary(dto)

        assertThat(result.forecastDays).hasSize(3)
    }

    @Test
    fun `mapForecastResponseToSummary formats location correctly`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToSummary(dto)

        assertThat(result.locationName).isEqualTo("Bogota, Colombia")
    }

    @Test
    fun `mapForecastResponseToDomain converts isDay correctly`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDomain(dto)

        assertThat(result.current.isDay).isTrue()
    }

    @Test
    fun `mapForecastResponseToDomain maps hourly forecast`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDomain(dto)

        assertThat(result.forecast[0].hourlyForecast).isEmpty()
    }

    @Test
    fun `mapForecastResponseToDomain maps astro data`() {
        val dto = TestDtoData.createForecastResponseDto()

        val result = mapper.mapForecastResponseToDomain(dto)

        assertThat(result.forecast[0].sunrise).isEqualTo("06:00 AM")
        assertThat(result.forecast[0].sunset).isEqualTo("06:00 PM")
        assertThat(result.forecast[0].moonPhase).isEqualTo("Waxing Crescent")
    }

    @Test
    fun `mapForecastResponseToDomain maps hourly forecast correctly`() {
        val hourDto = TestDtoData.createHourDto()
        val forecastDayDto = TestDtoData.createForecastDayDto().copy(hours = listOf(hourDto))
        val dto = TestDtoData.createForecastResponseDto().copy(
            forecast = ForecastDataDto(listOf(forecastDayDto))
        )

        val result = mapper.mapForecastResponseToDomain(dto)

        assertThat(result.forecast[0].hourlyForecast).hasSize(1)
        assertThat(result.forecast[0].hourlyForecast[0].time).isEqualTo(hourDto.time)
        assertThat(result.forecast[0].hourlyForecast[0].tempCelsius).isEqualTo(hourDto.tempCelsius)
        assertThat(result.forecast[0].hourlyForecast[0].isDay).isEqualTo(hourDto.isDay == 1)
    }

    @Test
    fun `mapForecastResponseToDomain converts hourly isDay flag correctly`() {
        val hourDtoDay = TestDtoData.createHourDto().copy(isDay = 1)
        val hourDtoNight = TestDtoData.createHourDto().copy(isDay = 0)
        val forecastDayDto = TestDtoData.createForecastDayDto().copy(hours = listOf(hourDtoDay, hourDtoNight))
        val dto = TestDtoData.createForecastResponseDto().copy(
            forecast = ForecastDataDto(listOf(forecastDayDto))
        )

        val result = mapper.mapForecastResponseToDomain(dto)

        assertThat(result.forecast[0].hourlyForecast[0].isDay).isTrue()
        assertThat(result.forecast[0].hourlyForecast[1].isDay).isFalse()
    }

    @Test
    fun `mapForecastResponseToDomain adds https to hourly condition icons`() {
        val hourDto = TestDtoData.createHourDto()
        val forecastDayDto = TestDtoData.createForecastDayDto().copy(hours = listOf(hourDto))
        val dto = TestDtoData.createForecastResponseDto().copy(
            forecast = ForecastDataDto(listOf(forecastDayDto))
        )

        val result = mapper.mapForecastResponseToDomain(dto)

        assertThat(result.forecast[0].hourlyForecast[0].condition.icon).startsWith("https://")
    }
}
