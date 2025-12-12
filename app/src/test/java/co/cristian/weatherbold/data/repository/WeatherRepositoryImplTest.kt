package co.cristian.weatherbold.data.repository

import app.cash.turbine.test
import co.cristian.weatherbold.base.TestDtoData
import co.cristian.weatherbold.core.network.NetworkConnectivityManager
import co.cristian.weatherbold.core.network.NetworkErrorHandler
import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.data.mapper.WeatherMapper
import co.cristian.weatherbold.data.remote.datasource.WeatherRemoteDataSource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private lateinit var remoteDataSource: WeatherRemoteDataSource
    private lateinit var weatherMapper: WeatherMapper
    private lateinit var networkConnectivityManager: NetworkConnectivityManager
    private lateinit var networkErrorHandler: NetworkErrorHandler
    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setup() {
        remoteDataSource = mockk()
        weatherMapper = WeatherMapper()
        networkConnectivityManager = mockk()
        networkErrorHandler = mockk()
        repository = WeatherRepositoryImpl(
            remoteDataSource,
            weatherMapper,
            networkConnectivityManager,
            networkErrorHandler
        )
    }

    @Test
    fun `searchLocation with network returns success`() = runTest {
        val locationDtos = TestDtoData.createLocationDtoList()
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.searchLocation("Bogota") } returns locationDtos

        repository.searchLocation("Bogota").test {
            assertThat(awaitItem()).isInstanceOf(NetworkResult.Loading::class.java)
            
            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Success::class.java)
            assertThat((result as NetworkResult.Success).data).hasSize(3)
            
            awaitComplete()
        }
    }

    @Test
    fun `searchLocation without network returns error`() = runTest {
        every { networkConnectivityManager.isNetworkAvailable() } returns false

        repository.searchLocation("Bogota").test {
            assertThat(awaitItem()).isInstanceOf(NetworkResult.Loading::class.java)
            
            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)
            assertThat((result as NetworkResult.Error).message).contains("connection")
            
            awaitComplete()
        }
    }

    @Test
    fun `searchLocation with API error returns error`() = runTest {
        val exception = RuntimeException("API Error")
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.searchLocation("Bogota") } throws exception
        every { networkErrorHandler.handleError(exception) } returns NetworkResult.Error("API Error")

        repository.searchLocation("Bogota").test {
            assertThat(awaitItem()).isInstanceOf(NetworkResult.Loading::class.java)
            
            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)
            
            awaitComplete()
        }
    }

    @Test
    fun `getWeatherDetail with network returns success`() = runTest {
        val forecastDto = TestDtoData.createForecastResponseDto()
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.getForecast("Bogota", 3) } returns forecastDto

        repository.getWeatherDetail("Bogota", 3).test {
            assertThat(awaitItem()).isInstanceOf(NetworkResult.Loading::class.java)
            
            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Success::class.java)
            val detail = (result as NetworkResult.Success).data
            assertThat(detail.locationName).contains("Bogota")
            assertThat(detail.threeDayForecast).hasSize(3)
            
            awaitComplete()
        }
    }

    @Test
    fun `getWeatherDetail without network returns error`() = runTest {
        every { networkConnectivityManager.isNetworkAvailable() } returns false

        repository.getWeatherDetail("Bogota").test {
            assertThat(awaitItem()).isInstanceOf(NetworkResult.Loading::class.java)
            
            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)
            assertThat((result as NetworkResult.Error).message).contains("internet")
            
            awaitComplete()
        }
    }

    @Test
    fun `getWeatherDetail with exception returns error`() = runTest {
        val exception = RuntimeException("Network error")
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.getForecast("Bogota", 3) } throws exception
        every { networkErrorHandler.handleError(exception) } returns NetworkResult.Error("Network error")

        repository.getWeatherDetail("Bogota").test {
            assertThat(awaitItem()).isInstanceOf(NetworkResult.Loading::class.java)
            
            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)
            
            awaitComplete()
        }
    }
}
