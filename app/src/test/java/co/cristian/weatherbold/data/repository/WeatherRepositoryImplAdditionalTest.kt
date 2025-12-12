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
import java.io.IOException

class WeatherRepositoryImplAdditionalTest {

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
    fun `searchLocation emits loading first`() = runTest {
        val locationDtos = TestDtoData.createLocationDtoList()
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.searchLocation("Bogota") } returns locationDtos

        repository.searchLocation("Bogota").test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(NetworkResult.Loading::class.java)
            
            awaitItem() // Success
            awaitComplete()
        }
    }

    @Test
    fun `getWeatherDetail emits loading first`() = runTest {
        val forecastDto = TestDtoData.createForecastResponseDto()
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.getForecast("Bogota", 3) } returns forecastDto

        repository.getWeatherDetail("Bogota", 3).test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(NetworkResult.Loading::class.java)
            
            awaitItem() // Success
            awaitComplete()
        }
    }

    @Test
    fun `searchLocation with IOException returns error`() = runTest {
        val exception = IOException("Network error")
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.searchLocation("Bogota") } throws exception
        every { networkErrorHandler.handleError(exception) } returns NetworkResult.Error("Network error")

        repository.searchLocation("Bogota").test {
            awaitItem() // Loading
            
            val error = awaitItem()
            assertThat(error).isInstanceOf(NetworkResult.Error::class.java)
            
            awaitComplete()
        }
    }

    @Test
    fun `getWeatherDetail with IOException returns error`() = runTest {
        val exception = IOException("Connection failed")
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.getForecast("Bogota", 3) } throws exception
        every { networkErrorHandler.handleError(exception) } returns NetworkResult.Error("Connection failed")

        repository.getWeatherDetail("Bogota", 3).test {
            awaitItem() // Loading
            
            val error = awaitItem()
            assertThat(error).isInstanceOf(NetworkResult.Error::class.java)
            
            awaitComplete()
        }
    }

    @Test
    fun `searchLocation maps DTOs correctly`() = runTest {
        val locationDtos = TestDtoData.createLocationDtoList()
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.searchLocation("Test") } returns locationDtos

        repository.searchLocation("Test").test {
            awaitItem() // Loading
            
            val success = awaitItem() as NetworkResult.Success
            assertThat(success.data[0].name).isEqualTo("Bogota")
            assertThat(success.data[1].name).isEqualTo("Medellin")
            assertThat(success.data[2].name).isEqualTo("Cali")
            
            awaitComplete()
        }
    }

    @Test
    fun `getWeatherDetail maps forecast correctly`() = runTest {
        val forecastDto = TestDtoData.createForecastResponseDto()
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.getForecast("Test", 5) } returns forecastDto

        repository.getWeatherDetail("Test", 5).test {
            awaitItem() // Loading
            
            val success = awaitItem() as NetworkResult.Success
            assertThat(success.data.locationName).contains("Bogota")
            assertThat(success.data.threeDayForecast).hasSize(3)
            
            awaitComplete()
        }
    }

    @Test
    fun `searchLocation with empty query still works`() = runTest {
        val locationDtos = emptyList<co.cristian.weatherbold.data.remote.dto.LocationDto>()
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.searchLocation("") } returns locationDtos

        repository.searchLocation("").test {
            awaitItem() // Loading
            
            val success = awaitItem() as NetworkResult.Success
            assertThat(success.data).isEmpty()
            
            awaitComplete()
        }
    }

    @Test
    fun `getWeatherDetail with different days parameter works`() = runTest {
        val forecastDto = TestDtoData.createForecastResponseDto()
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.getForecast("Bogota", 7) } returns forecastDto

        repository.getWeatherDetail("Bogota", 7).test {
            awaitItem() // Loading
            
            val success = awaitItem()
            assertThat(success).isInstanceOf(NetworkResult.Success::class.java)
            
            awaitComplete()
        }
    }
}
