package co.cristian.weatherbold.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.databinding.FragmentWeatherDetailBinding
import co.cristian.weatherbold.domain.model.WeatherDetail
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherDetailViewModel by viewModels()
    private val args: WeatherDetailFragmentArgs by navArgs()
    
    private lateinit var forecastAdapter: DayForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupRetryButton()
        observeWeatherDetail()
        
        // Load weather data
        viewModel.getWeatherDetail(args.locationName)
    }

    private fun setupRecyclerView() {
        forecastAdapter = DayForecastAdapter()
        binding.forecastRecyclerView.adapter = forecastAdapter
    }

    private fun setupRetryButton() {
        binding.retryButton.setOnClickListener {
            viewModel.retry()
        }
    }

    private fun observeWeatherDetail() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weatherDetail.collect { result ->
                when (result) {
                    is NetworkResult.Loading -> showLoading()
                    is NetworkResult.Success -> showWeatherDetail(result.data)
                    is NetworkResult.Error -> showError(result.message)
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            loadingProgress.isVisible = true
            contentContainer.isVisible = false
            errorLayout.isVisible = false
        }
    }

    private fun showWeatherDetail(detail: WeatherDetail) {
        binding.apply {
            loadingProgress.isVisible = false
            contentContainer.isVisible = true
            errorLayout.isVisible = false

            // Current weather
            val current = detail.currentWeather
            temperatureText.text = current.displayTemp
            conditionText.text = current.conditionText
            feelsLikeText.text = current.displayFeelsLike
            windText.text = current.displayWind
            humidityText.text = current.displayHumidity
            visibilityText.text = current.displayVisibility

            // Load weather icon with Coil
            val iconUrl = "https:${current.conditionIcon}"
            weatherIcon.load(iconUrl) {
                crossfade(true)
            }

            // 3-day forecast
            forecastAdapter.submitList(detail.threeDayForecast)
        }
    }

    private fun showError(message: String) {
        binding.apply {
            loadingProgress.isVisible = false
            contentContainer.isVisible = false
            errorLayout.isVisible = true
            errorMessage.text = message
        }
        
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
