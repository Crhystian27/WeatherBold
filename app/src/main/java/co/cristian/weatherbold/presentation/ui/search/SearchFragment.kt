package co.cristian.weatherbold.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.databinding.FragmentSearchBinding
import co.cristian.weatherbold.domain.model.Location
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Fragment for real-time location search
 * 
 * Features:
 * - Real-time search with 300ms debounce
 * - Minimum 3 characters to search
 * - States: Loading, Success, Error, Empty
 * - Loading effects in each card item
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: LocationSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchInput()
        observeSearchResults()
    }

    private fun setupRecyclerView() {
        adapter = LocationSearchAdapter { location ->
            onLocationSelected(location)
        }
        binding.locationsRecyclerView.adapter = adapter
    }

    /**
     * Setup search input with TextWatcher
     * Clear button ("X") handled automatically by TextInputLayout
     */
    private fun setupSearchInput() {
        binding.searchEditText.addTextChangedListener { text ->
            viewModel.onSearchQueryChanged(text?.toString() ?: "")
        }
    }

    private fun observeSearchResults() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResults.collect { result ->
                when (result) {
                    is NetworkResult.Loading -> showLoading()
                    is NetworkResult.Success -> showResults(result.data)
                    is NetworkResult.Error -> showError(result.message)
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            locationsRecyclerView.isVisible = false
            emptyStateLayout.isVisible = false
        }
    }

    private fun showResults(locations: List<Location>) {
        binding.apply {
            if (locations.isEmpty()) {
                val hasQuery = viewModel.searchQuery.value.trim().length >= 3
                emptyStateLayout.isVisible = hasQuery
                locationsRecyclerView.isVisible = false
            } else {
                emptyStateLayout.isVisible = false
                locationsRecyclerView.isVisible = true
                adapter.submitList(locations)
            }
        }
    }

    private fun showError(message: String) {
        binding.apply {
            val currentList = adapter.currentList
            if (currentList.isEmpty()) {
                locationsRecyclerView.isVisible = false
                emptyStateLayout.isVisible = false
            }
        }
        
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun onLocationSelected(location: Location) {
        // TODO: Navigate to WeatherDetailFragment
        // findNavController().navigate(
        //     SearchFragmentDirections.actionSearchToDetail(location.name)
        // )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
