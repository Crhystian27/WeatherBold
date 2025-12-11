package co.cristian.weatherbold.data.repository

import app.cash.turbine.test
import co.cristian.weatherbold.base.TestData
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

class WeatherRepositoryImplTest {
    
    private lateinit var remoteDataSource: WeatherRemoteDataSource
    private lateinit var weatherMapper: WeatherMapper
    private lateinit var networkConnectivityManager: NetworkConnectivityManager
    private lateinit var networkErrorHandler: NetworkErrorHandler
    private lateinit var repository: WeatherRepositoryImpl
    
    @Before
    fun setup() {
        remoteDataSource = mockk(relaxed = true)
        weatherMapper = mockk(relaxed = true)
        networkConnectivityManager = mockk(relaxed = true)
        networkErrorHandler = mockk(relaxed = true)
        
        repository = WeatherRepositoryImpl(
            remoteDataSource,
            weatherMapper,
            networkConnectivityManager,
            networkErrorHandler
        )
    }
    
    @Test
    fun `searchLocation emits loading then success`() = runTest {
        val query = "Bogota"
        val locationDtos = TestDtoData.createLocationDtoList()
        val locations = TestData.createLocationList()
        
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.searchLocation(query) } returns locationDtos
        every { weatherMapper.mapLocationDtoListToDomain(locationDtos) } returns locations
        
        repository.searchLocation(query).test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(NetworkResult.Loading::class.java)
            
            val success = awaitItem()
            assertThat(success).isInstanceOf(NetworkResult.Success::class.java)
            assertThat((success as NetworkResult.Success).data).hasSize(3)
            
            awaitComplete()
        }
    }
    
    @Test
    fun `searchLocation returns error when no network`() = runTest {
        val query = "Bogota"
        every { networkConnectivityManager.isNetworkAvailable() } returns false
        
        repository.searchLocation(query).test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(NetworkResult.Loading::class.java)
            
            val error = awaitItem()
            assertThat(error).isInstanceOf(NetworkResult.Error::class.java)
            assertThat((error as NetworkResult.Error).message).contains("conexión")
            
            awaitComplete()
        }
    }
    
    @Test
    fun `searchLocation handles exception`() = runTest {
        val query = "Bogota"
        val exception = IOException("Network error")
        
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.searchLocation(query) } throws exception
        every { networkErrorHandler.handleError(exception) } returns NetworkResult.Error("Network error", null)
        
        repository.searchLocation(query).test {
            skipItems(1) // Skip loading
            
            val error = awaitItem()
            assertThat(error).isInstanceOf(NetworkResult.Error::class.java)
            
            awaitComplete()
        }
    }
    
    @Test
    fun `getWeatherDetail emits loading then success`() = runTest {
        val location = "Bogota"
        val forecastDto = TestDtoData.createForecastDto()
        val weatherDetail = TestData.createWeatherDetail()
        
        every { networkConnectivityManager.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.getForecast(location, 3) } returns forecastDto
        every { weatherMapper.mapForecastResponseToDetail(forecastDto) } returns weatherDetail
        
        repository.getWeatherDetail(location, 3).test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(NetworkResult.Loading::class.java)
            
            val success = awaitItem()
            assertThat(success).isInstanceOf(NetworkResult.Success::class.java)
            assertThat((success as NetworkResult.Success).data.locationName).contains("Bogota")
            
            awaitComplete()
        }
    }
    
    @Test
    fun `getWeatherDetail returns error when no network`() = runTest {
        val location = "Bogota"
        every { networkConnectivityManager.isNetworkAvailable() } returns false
        
        repository.getWeatherDetail(location, 3).test {
            skipItems(1) // Skip loading
            
            val error = awaitItem()
            assertThat(error).isInstanceOf(NetworkResult.Error::class.java)
            assertThat((error as NetworkResult.Error).message).contains("internet")
            
            awaitComplete()
        }
    }
    

}
