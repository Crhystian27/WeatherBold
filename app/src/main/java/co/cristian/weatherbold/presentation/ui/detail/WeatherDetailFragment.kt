package co.cristian.weatherbold.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.core.util.WeatherFormatter
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
        
        setupLocationName()
        setupRecyclerView()
        setupRetryButton()
        observeWeatherDetail()
        
        // Load weather data
        viewModel.getWeatherDetail(args.locationName)
    }

    private fun setupLocationName() {
        // Display location name with country if available
        val locationDisplay = if (args.locationCountry != null) {
            "${args.locationName}, ${args.locationCountry}"
        } else {
            args.locationName
        }
        binding.locationNameText.text = locationDisplay
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
        binding.shimmerLayout.root.visibility = View.VISIBLE
        binding.contentContainer.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
    }

    private fun showWeatherDetail(detail: WeatherDetail) {
        binding.shimmerLayout.root.visibility = View.GONE
        binding.contentContainer.visibility = View.VISIBLE
        binding.errorLayout.visibility = View.GONE

        // Current weather using WeatherFormatter
        val current = detail.currentWeather
        val ctx = requireContext()
        binding.temperatureText.text = WeatherFormatter.formatTemperature(ctx, current.tempCelsius)
        binding.conditionText.text = current.conditionText
        binding.feelsLikeText.text = WeatherFormatter.formatFeelsLike(ctx, current.feelsLikeCelsius)
        binding.windText.text = WeatherFormatter.formatWind(ctx, current.windKph, current.windDirection)
        binding.humidityText.text = WeatherFormatter.formatHumidity(ctx, current.humidity)
        binding.visibilityText.text = WeatherFormatter.formatVisibility(ctx, current.visibilityKm)

        // Load weather icon with Coil (URL already has https: protocol from mapper)
        binding.weatherIcon.load(current.conditionIcon) {
            crossfade(true)
        }

        // 3-day forecast
        forecastAdapter.submitList(detail.threeDayForecast)
    }

    private fun showError(message: String) {
        binding.shimmerLayout.root.visibility = View.GONE
        binding.contentContainer.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
        binding.errorMessage.text = message
        
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
