package co.cristian.weatherbold.data.remote.datasource

import co.cristian.weatherbold.base.TestDtoData
import co.cristian.weatherbold.data.remote.api.WeatherApiService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherRemoteDataSourceAdditionalTest {

    private lateinit var apiService: WeatherApiService
    private lateinit var dataSource: WeatherRemoteDataSource

    @Before
    fun setup() {
        apiService = mockk()
        dataSource = WeatherRemoteDataSource(apiService)
    }

    @Test
    fun `searchLocation returns empty list when no results`() = runTest {
        coEvery { apiService.searchLocation("XYZ") } returns emptyList()

        val result = dataSource.searchLocation("XYZ")

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { apiService.searchLocation("XYZ") }
    }

    @Test
    fun `getForecast with different days parameter works`() = runTest {
        val forecastDto = TestDtoData.createForecastResponseDto()
        coEvery { apiService.getForecast("Bogota", 7) } returns forecastDto

        val result = dataSource.getForecast("Bogota", 7)

        assertThat(result).isNotNull()
        coVerify(exactly = 1) { apiService.getForecast("Bogota", 7) }
    }

    @Test
    fun `searchLocation with special characters works`() = runTest {
        val locationDtos = TestDtoData.createLocationDtoList()
        coEvery { apiService.searchLocation("São Paulo") } returns locationDtos

        val result = dataSource.searchLocation("São Paulo")

        assertThat(result).hasSize(3)
        coVerify { apiService.searchLocation("São Paulo") }
    }

    @Test
    fun `getForecast returns correct location data`() = runTest {
        val forecastDto = TestDtoData.createForecastResponseDto()
        coEvery { apiService.getForecast("Test", 3) } returns forecastDto

        val result = dataSource.getForecast("Test", 3)

        assertThat(result.location.name).isEqualTo("Bogota")
        assertThat(result.location.country).isEqualTo("Colombia")
    }

    @Test
    fun `searchLocation with single result works`() = runTest {
        val singleLocation = listOf(TestDtoData.createLocationDto())
        coEvery { apiService.searchLocation("Bogota") } returns singleLocation

        val result = dataSource.searchLocation("Bogota")

        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("Bogota")
    }
}
