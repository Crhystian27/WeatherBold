package co.cristian.weatherbold.domain.usecase

import app.cash.turbine.test
import co.cristian.weatherbold.base.TestData
import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.domain.repository.WeatherRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetWeatherDetailUseCaseTest {
    
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var useCase: GetWeatherDetailUseCase
    
    @Before
    fun setup() {
        weatherRepository = mockk(relaxed = true)
        useCase = GetWeatherDetailUseCase(weatherRepository)
    }
    
    @Test
    fun `invoke calls repository with correct parameters`() = runTest {
        val location = "Bogota"
        val days = 3
        val weatherDetail = TestData.createWeatherDetail()
        every { weatherRepository.getWeatherDetail(location, days) } returns flowOf(
            NetworkResult.Success(weatherDetail)
        )
        
        useCase(location, days).test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Success::class.java)
            assertThat((result as NetworkResult.Success).data.locationName).contains("Bogota")
            awaitComplete()
        }
        
        verify { weatherRepository.getWeatherDetail(location, days) }
    }
    
    @Test
    fun `invoke uses default days parameter`() = runTest {
        val location = "London"
        val weatherDetail = TestData.createWeatherDetail()
        every { weatherRepository.getWeatherDetail(location, 3) } returns flowOf(
            NetworkResult.Success(weatherDetail)
        )
        
        useCase(location).test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Success::class.java)
            awaitComplete()
        }
        
        verify { weatherRepository.getWeatherDetail(location, 3) }
    }
    
    @Test
    fun `invoke returns error when repository fails`() = runTest {
        val location = "Invalid"
        val errorMessage = "Location not found"
        every { weatherRepository.getWeatherDetail(location, 3) } returns flowOf(
            NetworkResult.Error(errorMessage, 404)
        )
        
        useCase(location).test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)
            assertThat((result as NetworkResult.Error).message).isEqualTo(errorMessage)
            assertThat(result.code).isEqualTo(404)
            awaitComplete()
        }
    }
}
