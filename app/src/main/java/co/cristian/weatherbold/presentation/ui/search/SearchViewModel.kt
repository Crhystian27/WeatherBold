package co.cristian.weatherbold.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.cristian.weatherbold.core.network.ApiConstants
import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.domain.model.Location
import co.cristian.weatherbold.domain.usecase.SearchLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for location search with real-time debounce
 * 
 * Features:
 * - Configurable debounce delay
 * - Minimum character validation
 * - Distinct to avoid duplicate searches
 * - Automatic cancellation of previous searches
 */
@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchLocationUseCase: SearchLocationUseCase
) : ViewModel() {
    
    companion object {
        const val MIN_SEARCH_LENGTH = ApiConstants.MIN_SEARCH_QUERY_LENGTH
        private const val DEBOUNCE_DELAY = ApiConstants.SEARCH_DEBOUNCE_DELAY
    }
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _searchResults = MutableStateFlow<NetworkResult<List<Location>>>(NetworkResult.Loading)
    val searchResults: StateFlow<NetworkResult<List<Location>>> = _searchResults.asStateFlow()
    
    init {
        setupSearchDebounce()
    }
    
    /**
     * Flow: searchQuery → debounce → filter → distinct → search
     */
    private fun setupSearchDebounce() {
        viewModelScope.launch {
            _searchQuery
                .debounce(DEBOUNCE_DELAY)
                .map { it.trim() }
                .filter { it.length >= MIN_SEARCH_LENGTH }
                .distinctUntilChanged()
                .collectLatest { query ->
                    searchLocation(query)
                }
        }
    }
    
    /**
     * Update search query
     * Debounce handles search execution
     */
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        
        // Clear results if query too short
        if (query.trim().length < MIN_SEARCH_LENGTH) {
            _searchResults.value = NetworkResult.Loading
        }
    }
    
    /**
     * Execute location search
     * UseCase and Repository handle connectivity check
     */
    private suspend fun searchLocation(query: String) {
        searchLocationUseCase(query).collect { result ->
            _searchResults.value = result
        }
    }
}
