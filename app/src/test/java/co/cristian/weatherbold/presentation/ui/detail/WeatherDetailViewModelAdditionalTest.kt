package co.cristian.weatherbold.presentation.ui.detail

import app.cash.turbine.test
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
class WeatherDetailViewModelAdditionalTest : BaseViewModelTest() {

    private lateinit var getWeatherDetailUseCase: GetWeatherDetailUseCase
    private lateinit var viewModel: WeatherDetailViewModel

    @Before
    override fun setup() {
        super.setup()
        getWeatherDetailUseCase = mockk(relaxed = true)
        viewModel = WeatherDetailViewModel(getWeatherDetailUseCase)
    }

    @Test
    fun `getWeatherDetail with different location updates state`() = runTest {
        val detail1 = TestData.createWeatherDetail()
        val detail2 = TestData.createWeatherDetail()
        
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(NetworkResult.Success(detail1))
        coEvery { getWeatherDetailUseCase("Medellin", 3) } returns flowOf(NetworkResult.Success(detail2))

        viewModel.getWeatherDetail("Bogota")
        testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.currentLocation.value).isEqualTo("Bogota")

        viewModel.getWeatherDetail("Medellin")
        testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.currentLocation.value).isEqualTo("Medellin")
    }

    @Test
    fun `multiple calls to getWeatherDetail update location correctly`() = runTest {
        val detail = TestData.createWeatherDetail()
        coEvery { getWeatherDetailUseCase(any(), any()) } returns flowOf(NetworkResult.Success(detail))

        viewModel.getWeatherDetail("Location1")
        testDispatcher.scheduler.advanceUntilIdle()
        
        viewModel.getWeatherDetail("Location2")
        testDispatcher.scheduler.advanceUntilIdle()
        
        viewModel.getWeatherDetail("Location3")
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.currentLocation.value).isEqualTo("Location3")
    }

    @Test
    fun `clearState after successful load resets state`() = runTest {
        val detail = TestData.createWeatherDetail()
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(NetworkResult.Success(detail))

        viewModel.getWeatherDetail("Bogota")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.weatherDetail.test {
            skipItems(1) // Skip current success state
            
            viewModel.clearState()

            val result = awaitItem()
            assertThat(result).isInstanceOf(NetworkResult.Loading::class.java)
        }
    }

    @Test
    fun `clearState after error resets state`() = runTest {
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(
            NetworkResult.Error("Error")
        )

        viewModel.getWeatherDetail("Bogota")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.clearState()

        assertThat(viewModel.weatherDetail.value).isInstanceOf(NetworkResult.Loading::class.java)
        assertThat(viewModel.currentLocation.value).isNull()
    }

    @Test
    fun `getWeatherDetail with loading state emits correctly`() = runTest {
        val detail = TestData.createWeatherDetail()
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(
            NetworkResult.Loading,
            NetworkResult.Success(detail)
        )

        viewModel.getWeatherDetail("Bogota")
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.currentLocation.value).isEqualTo("Bogota")
    }

    @Test
    fun `retry with error state calls use case again`() = runTest {
        val detail = TestData.createWeatherDetail()
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(
            NetworkResult.Error("Error")
        ) andThen flowOf(
            NetworkResult.Success(detail)
        )

        viewModel.getWeatherDetail("Bogota")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.retry()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.currentLocation.value).isEqualTo("Bogota")
    }
}
