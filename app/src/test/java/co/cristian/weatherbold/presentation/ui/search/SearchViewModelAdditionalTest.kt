package co.cristian.weatherbold.presentation.ui.search

import app.cash.turbine.test
import co.cristian.weatherbold.base.BaseViewModelTest
import co.cristian.weatherbold.base.TestData
import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.domain.usecase.SearchLocationUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelAdditionalTest : BaseViewModelTest() {

    private lateinit var searchLocationUseCase: SearchLocationUseCase
    private lateinit var viewModel: SearchViewModel

    @Before
    override fun setup() {
        super.setup()
        searchLocationUseCase = mockk(relaxed = true)
    }

    @Test
    fun `initial search query is empty`() = runTest {
        viewModel = SearchViewModel(searchLocationUseCase)

        assertThat(viewModel.searchQuery.value).isEmpty()
    }

    @Test
    fun `search with minimum length triggers search`() = runTest {
        val locations = TestData.createLocationList()
        coEvery { searchLocationUseCase("Bog") } returns flowOf(
            NetworkResult.Success(locations)
        )

        viewModel = SearchViewModel(searchLocationUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.searchResults.test {
            skipItems(1)

            viewModel.onSearchQueryChanged("Bog")
            testDispatcher.scheduler.advanceTimeBy(700)

            val emission = awaitItem()
            assertThat(emission).isInstanceOf(NetworkResult.Success::class.java)
        }
    }

    @Test
    fun `search with exactly min length works`() = runTest {
        val locations = TestData.createLocationList()
        coEvery { searchLocationUseCase("ABC") } returns flowOf(
            NetworkResult.Success(locations)
        )

        viewModel = SearchViewModel(searchLocationUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onSearchQueryChanged("ABC")
        testDispatcher.scheduler.advanceTimeBy(700)

        assertThat(viewModel.searchQuery.value).isEqualTo("ABC")
    }

    @Test
    fun `search with whitespace only clears results`() = runTest {
        viewModel = SearchViewModel(searchLocationUseCase)

        viewModel.onSearchQueryChanged("   ")
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.searchResults.value).isInstanceOf(NetworkResult.Loading::class.java)
    }

    @Test
    fun `search updates query immediately`() = runTest {
        viewModel = SearchViewModel(searchLocationUseCase)

        viewModel.onSearchQueryChanged("Test Query")

        assertThat(viewModel.searchQuery.value).isEqualTo("Test Query")
    }

    @Test
    fun `multiple searches with same query are distinct`() = runTest {
        val locations = TestData.createLocationList()
        coEvery { searchLocationUseCase("Bogota") } returns flowOf(
            NetworkResult.Success(locations)
        )

        viewModel = SearchViewModel(searchLocationUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.searchResults.test {
            skipItems(1)

            viewModel.onSearchQueryChanged("Bogota")
            testDispatcher.scheduler.advanceTimeBy(700)
            awaitItem()

            // Same query should not trigger another search
            viewModel.onSearchQueryChanged("Bogota")
            testDispatcher.scheduler.advanceTimeBy(700)

            expectNoEvents()
        }
    }

    @Test
    fun `search with loading result emits correctly`() = runTest {
        val locations = TestData.createLocationList()
        coEvery { searchLocationUseCase("Bogota") } returns flowOf(
            NetworkResult.Success(locations)
        )

        viewModel = SearchViewModel(searchLocationUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.searchResults.test {
            skipItems(1)

            viewModel.onSearchQueryChanged("Bogota")
            testDispatcher.scheduler.advanceTimeBy(700)

            val success = awaitItem()
            assertThat(success).isInstanceOf(NetworkResult.Success::class.java)
        }
    }

    @Test
    fun `search with empty result list returns success`() = runTest {
        coEvery { searchLocationUseCase("XYZ") } returns flowOf(
            NetworkResult.Success(emptyList())
        )

        viewModel = SearchViewModel(searchLocationUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.searchResults.test {
            skipItems(1)

            viewModel.onSearchQueryChanged("XYZ")
            testDispatcher.scheduler.advanceTimeBy(700)

            val emission = awaitItem()
            assertThat(emission).isInstanceOf(NetworkResult.Success::class.java)
            assertThat((emission as NetworkResult.Success).data).isEmpty()
        }
    }

    @Test
    fun `query below minimum length keeps loading state`() = runTest {
        viewModel = SearchViewModel(searchLocationUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onSearchQueryChanged("Bo")
        testDispatcher.scheduler.advanceUntilIdle()

        // Should remain in Loading state when query is too short
        assertThat(viewModel.searchResults.value).isInstanceOf(NetworkResult.Loading::class.java)
        assertThat(viewModel.searchQuery.value).isEqualTo("Bo")
    }
}
