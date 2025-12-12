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

class WeatherRemoteDataSourceTest {

    private lateinit var apiService: WeatherApiService
    private lateinit var dataSource: WeatherRemoteDataSource

    @Before
    fun setup() {
        apiService = mockk()
        dataSource = WeatherRemoteDataSource(apiService)
    }

    @Test
    fun `searchLocation calls API service`() = runTest {
        val locationDtos = TestDtoData.createLocationDtoList()
        coEvery { apiService.searchLocation("Bogota") } returns locationDtos

        val result = dataSource.searchLocation("Bogota")

        assertThat(result).hasSize(3)
        coVerify { apiService.searchLocation("Bogota") }
    }

    @Test
    fun `getForecast calls API service with correct parameters`() = runTest {
        val forecastDto = TestDtoData.createForecastResponseDto()
        coEvery { apiService.getForecast("Bogota", 3) } returns forecastDto

        val result = dataSource.getForecast("Bogota", 3)

        assertThat(result.location.name).isEqualTo("Bogota")
        coVerify { apiService.getForecast("Bogota", 3) }
    }

    @Test
    fun `getForecast returns correct forecast data`() = runTest {
        val forecastDto = TestDtoData.createForecastResponseDto()
        coEvery { apiService.getForecast("Medellin", 5) } returns forecastDto

        val result = dataSource.getForecast("Medellin", 5)

        assertThat(result.forecast.forecastDays).hasSize(3)
        coVerify { apiService.getForecast("Medellin", 5) }
    }
}
