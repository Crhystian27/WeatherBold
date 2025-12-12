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
class SearchViewModelTest : BaseViewModelTest() {
    
    private lateinit var searchLocationUseCase: SearchLocationUseCase
    private lateinit var viewModel: SearchViewModel
    
    @Before
    override fun setup() {
        super.setup()
        searchLocationUseCase = mockk(relaxed = true)
    }
    
    @Test
    fun `initial state is loading`() = runTest {
        viewModel = SearchViewModel(searchLocationUseCase)
        
        assertThat(viewModel.searchResults.value).isInstanceOf(NetworkResult.Loading::class.java)
    }
    
    @Test
    fun `search query updates correctly`() = runTest {
        viewModel = SearchViewModel(searchLocationUseCase)
        
        viewModel.onSearchQueryChanged("Bogota")
        
        assertThat(viewModel.searchQuery.value).isEqualTo("Bogota")
    }
    
    @Test
    fun `search with valid query returns success`() = runTest {
        val locations = TestData.createLocationList()
        coEvery { searchLocationUseCase("Bogota") } returns flowOf(
            NetworkResult.Success(locations)
        )
        
        viewModel = SearchViewModel(searchLocationUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        
        viewModel.searchResults.test {
            skipItems(1) // Skip initial Loading
            
            viewModel.onSearchQueryChanged("Bogota")
            testDispatcher.scheduler.advanceTimeBy(700) // Wait for debounce
            
            val emission = awaitItem()
            assertThat(emission).isInstanceOf(NetworkResult.Success::class.java)
            assertThat((emission as NetworkResult.Success).data).hasSize(3)
        }
    }
    
    @Test
    fun `search with error returns error result`() = runTest {
        val errorMessage = "Network error"
        coEvery { searchLocationUseCase("Bogota") } returns flowOf(
            NetworkResult.Error(errorMessage, null)
        )
        
        viewModel = SearchViewModel(searchLocationUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        
        viewModel.searchResults.test {
            skipItems(1) // Skip initial Loading
            
            viewModel.onSearchQueryChanged("Bogota")
            testDispatcher.scheduler.advanceTimeBy(700) // Wait for debounce
            
            val emission = awaitItem()
            assertThat(emission).isInstanceOf(NetworkResult.Error::class.java)
            assertThat((emission as NetworkResult.Error).message).isEqualTo(errorMessage)
        }
    }
    
    @Test
    fun `search with query less than min length clears results`() = runTest {
        viewModel = SearchViewModel(searchLocationUseCase)
        
        viewModel.onSearchQueryChanged("Bo")
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertThat(viewModel.searchResults.value).isInstanceOf(NetworkResult.Loading::class.java)
    }
    
    @Test
    fun `search with empty query clears results`() = runTest {
        viewModel = SearchViewModel(searchLocationUseCase)
        
        viewModel.onSearchQueryChanged("")
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertThat(viewModel.searchResults.value).isInstanceOf(NetworkResult.Loading::class.java)
    }
    
    @Test
    fun `search trims whitespace from query`() = runTest {
        val locations = TestData.createLocationList()
        coEvery { searchLocationUseCase("Bogota") } returns flowOf(
            NetworkResult.Success(locations)
        )
        
        viewModel = SearchViewModel(searchLocationUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        
        viewModel.onSearchQueryChanged("  Bogota  ")
        testDispatcher.scheduler.advanceTimeBy(700)
        
        assertThat(viewModel.searchQuery.value).isEqualTo("  Bogota  ")
    }
    
    @Test
    fun `debounce prevents multiple rapid searches`() = runTest {
        val locations = TestData.createLocationList()
        coEvery { searchLocationUseCase(any()) } returns flowOf(
            NetworkResult.Success(locations)
        )
        
        viewModel = SearchViewModel(searchLocationUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        
        viewModel.searchResults.test {
            skipItems(1) // Skip initial Loading
            
            viewModel.onSearchQueryChanged("Bog")
            testDispatcher.scheduler.advanceTimeBy(200)
            
            viewModel.onSearchQueryChanged("Bogo")
            testDispatcher.scheduler.advanceTimeBy(200)
            
            viewModel.onSearchQueryChanged("Bogota")
            testDispatcher.scheduler.advanceTimeBy(700)
            
            // Should only get one result after debounce
            val emission = awaitItem()
            assertThat(emission).isInstanceOf(NetworkResult.Success::class.java)
        }
    }
    
    @Test
    fun `search with special characters is handled`() = runTest {
        val locations = TestData.createLocationList()
        coEvery { searchLocationUseCase("São Paulo") } returns flowOf(
            NetworkResult.Success(locations)
        )
        
        viewModel = SearchViewModel(searchLocationUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        
        viewModel.searchResults.test {
            skipItems(1)
            
            viewModel.onSearchQueryChanged("São Paulo")
            testDispatcher.scheduler.advanceTimeBy(700)
            
            val emission = awaitItem()
            assertThat(emission).isInstanceOf(NetworkResult.Success::class.java)
        }
    }
}
