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

class SearchLocationUseCaseTest {
    
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var useCase: SearchLocationUseCase
    
    @Before
    fun setup() {
        weatherRepository = mockk(relaxed = true)
        useCase = SearchLocationUseCase(weatherRepository)
    }
    
    @Test
    fun `invoke calls repository with correct query`() = runTest {
        val query = "Bogota"
        val locations = TestData.createLocationList()
        every { weatherRepository.searchLocation(query) } returns flowOf(
            NetworkResult.Success(locations)
        )
        
        useCase(query).test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Success::class.java)
            assertThat((result as NetworkResult.Success).data).hasSize(3)
            awaitComplete()
        }
        
        verify { weatherRepository.searchLocation(query) }
    }
    
    @Test
    fun `invoke returns error when repository fails`() = runTest {
        val query = "Invalid"
        val errorMessage = "Network error"
        every { weatherRepository.searchLocation(query) } returns flowOf(
            NetworkResult.Error(errorMessage, null)
        )
        
        useCase(query).test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)
            assertThat((result as NetworkResult.Error).message).isEqualTo(errorMessage)
            awaitComplete()
        }
    }
    
    @Test
    fun `invoke returns loading state`() = runTest {
        val query = "London"
        every { weatherRepository.searchLocation(query) } returns flowOf(
            NetworkResult.Loading
        )
        
        useCase(query).test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Loading::class.java)
            awaitComplete()
        }
    }
}
