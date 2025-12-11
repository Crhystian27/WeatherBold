package co.cristian.weatherbold.presentation.ui.detail

import co.cristian.weatherbold.base.BaseViewModelTest
import co.cristian.weatherbold.base.TestData
import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.domain.usecase.GetWeatherDetailUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherDetailViewModelTest : BaseViewModelTest() {
    
    private lateinit var getWeatherDetailUseCase: GetWeatherDetailUseCase
    private lateinit var viewModel: WeatherDetailViewModel
    
    @Before
    override fun setup() {
        super.setup()
        getWeatherDetailUseCase = mockk(relaxed = true)
        viewModel = WeatherDetailViewModel(getWeatherDetailUseCase)
    }
    
    @Test
    fun `initial state is loading`() = runTest {
        assertThat(viewModel.weatherDetail.value).isInstanceOf(NetworkResult.Loading::class.java)
    }
    
    @Test
    fun `get weather detail updates current location`() = runTest {
        val weatherDetail = TestData.createWeatherDetail()
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(
            NetworkResult.Success(weatherDetail)
        )
        
        assertThat(viewModel.currentLocation.value).isNull()
        
        viewModel.getWeatherDetail("Bogota")
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertThat(viewModel.currentLocation.value).isEqualTo("Bogota")
    }
    
    @Test
    fun `get weather detail with error returns error result`() = runTest {
        val errorMessage = "Network error"
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(
            NetworkResult.Error(errorMessage, null)
        )
        
        viewModel.getWeatherDetail("Bogota")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val result = viewModel.weatherDetail.value
        assertThat(result).isInstanceOf(NetworkResult.Error::class.java)
        assertThat((result as NetworkResult.Error).message).isEqualTo(errorMessage)
    }
    
    @Test
    fun `retry does nothing when no location set`() = runTest {
        val initialState = viewModel.weatherDetail.value
        
        viewModel.retry()
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertThat(viewModel.weatherDetail.value).isEqualTo(initialState)
    }
    
    @Test
    fun `clear state resets to loading and null location`() = runTest {
        val weatherDetail = TestData.createWeatherDetail()
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(
            NetworkResult.Success(weatherDetail)
        )
        
        viewModel.getWeatherDetail("Bogota")
        testDispatcher.scheduler.advanceUntilIdle()
        
        viewModel.clearState()
        
        assertThat(viewModel.weatherDetail.value).isInstanceOf(NetworkResult.Loading::class.java)
        assertThat(viewModel.currentLocation.value).isNull()
    }
}
