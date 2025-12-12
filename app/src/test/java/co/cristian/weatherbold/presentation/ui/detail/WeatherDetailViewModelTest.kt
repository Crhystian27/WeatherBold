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
        assertThat(viewModel.currentLocation.value).isNull()
    }

    @Test
    fun `getWeatherDetail updates currentLocation`() = runTest {
        val detail = TestData.createWeatherDetail()
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(
            NetworkResult.Success(detail)
        )

        viewModel.getWeatherDetail("Bogota")
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.currentLocation.value).isEqualTo("Bogota")
    }

    @Test
    fun `getWeatherDetail emits success`() = runTest {
        val detail = TestData.createWeatherDetail()
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(
            NetworkResult.Success(detail)
        )

        viewModel.getWeatherDetail("Bogota")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.weatherDetail.test {
            val success = awaitItem()
            assertThat(success).isInstanceOf(NetworkResult.Success::class.java)
            assertThat((success as NetworkResult.Success).data.locationName).contains("Bogota")
        }
    }

    @Test
    fun `getWeatherDetail emits error`() = runTest {
        val errorMessage = "Network error"
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(
            NetworkResult.Error(errorMessage)
        )

        viewModel.weatherDetail.test {
            skipItems(1) // Skip initial Loading
            
            viewModel.getWeatherDetail("Bogota")
            testDispatcher.scheduler.advanceUntilIdle()

            val error = awaitItem()
            assertThat(error).isInstanceOf(NetworkResult.Error::class.java)
            assertThat((error as NetworkResult.Error).message).isEqualTo(errorMessage)
        }
    }

    @Test
    fun `retry calls getWeatherDetail with saved location`() = runTest {
        val detail = TestData.createWeatherDetail()
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(
            NetworkResult.Success(detail)
        )

        viewModel.getWeatherDetail("Bogota")
        testDispatcher.scheduler.advanceUntilIdle()

        // Retry should call the use case again
        viewModel.retry()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.currentLocation.value).isEqualTo("Bogota")
    }

    @Test
    fun `retry does nothing when no location saved`() = runTest {
        viewModel.retry()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.currentLocation.value).isNull()
        assertThat(viewModel.weatherDetail.value).isInstanceOf(NetworkResult.Loading::class.java)
    }

    @Test
    fun `clearState resets to initial state`() = runTest {
        val detail = TestData.createWeatherDetail()
        coEvery { getWeatherDetailUseCase("Bogota", 3) } returns flowOf(
            NetworkResult.Success(detail)
        )

        viewModel.getWeatherDetail("Bogota")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.clearState()

        assertThat(viewModel.weatherDetail.value).isInstanceOf(NetworkResult.Loading::class.java)
        assertThat(viewModel.currentLocation.value).isNull()
    }

    @Test
    fun `getWeatherDetail with custom days parameter`() = runTest {
        val detail = TestData.createWeatherDetail()
        coEvery { getWeatherDetailUseCase("Bogota", 5) } returns flowOf(
            NetworkResult.Success(detail)
        )

        viewModel.getWeatherDetail("Bogota", 5)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.currentLocation.value).isEqualTo("Bogota")
    }
}
