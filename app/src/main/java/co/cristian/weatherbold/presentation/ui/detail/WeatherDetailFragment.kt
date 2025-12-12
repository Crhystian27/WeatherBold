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
        
        setupToolbarTitle()
        setupLocationName()
        setupRecyclerView()
        setupRetryButton()
        observeWeatherDetail()
        
        // Load weather data
        viewModel.getWeatherDetail(args.locationName)
    }

    private fun setupToolbarTitle() {
        // Set toolbar title with emoji flag and location name
        val countryEmoji = getCountryEmoji(args.locationCountry ?: "")
        val toolbarTitle = "$countryEmoji ${args.locationName}"
        requireActivity().title = toolbarTitle
    }

    private fun setupLocationName() {
        // Display location name with region and country (2 lines)
        val locationDisplay = buildString {
            append(args.locationName)
            
            if (!args.locationRegion.isNullOrBlank()) {
                append(", ")
                append(args.locationRegion)
            }
            
            if (!args.locationCountry.isNullOrBlank()) {
                append("\n")
                append(args.locationCountry)
            }
        }
        binding.locationNameText.text = locationDisplay
    }
    
    /**
     * Returns country emoji flag based on country name
     */
    private fun getCountryEmoji(country: String): String {
        return when (country.lowercase().trim()) {
            // Latin America
            "colombia" -> "🇨🇴"
            "mexico" -> "🇲🇽"
            "argentina" -> "🇦🇷"
            "brazil" -> "🇧🇷"
            "chile" -> "🇨🇱"
            "peru" -> "🇵🇪"
            "venezuela" -> "🇻🇪"
            "ecuador" -> "🇪🇨"
            "bolivia" -> "🇧🇴"
            "paraguay" -> "🇵🇾"
            "uruguay" -> "🇺🇾"
            "costa rica" -> "🇨🇷"
            "panama" -> "🇵🇦"
            "guatemala" -> "🇬🇹"
            "honduras" -> "🇭🇳"
            "el salvador" -> "🇸🇻"
            "nicaragua" -> "🇳🇮"
            "cuba" -> "🇨🇺"
            "dominican republic" -> "🇩🇴"
            "puerto rico" -> "🇵🇷"
            
            // North America
            "united states", "usa", "united states of america" -> "🇺🇸"
            "canada" -> "🇨🇦"
            
            // Europe
            "spain", "españa" -> "🇪🇸"
            "united kingdom", "uk", "england", "scotland", "wales" -> "🇬🇧"
            "france" -> "🇫🇷"
            "germany" -> "🇩🇪"
            "italy" -> "🇮🇹"
            "portugal" -> "🇵🇹"
            "netherlands" -> "🇳🇱"
            "belgium" -> "🇧🇪"
            "switzerland" -> "🇨🇭"
            "austria" -> "🇦🇹"
            "sweden" -> "🇸🇪"
            "norway" -> "🇳🇴"
            "denmark" -> "🇩🇰"
            "finland" -> "🇫🇮"
            "poland" -> "🇵🇱"
            "russia", "russian federation" -> "🇷🇺"
            "greece" -> "🇬🇷"
            "ireland" -> "🇮🇪"
            "czech republic", "czechia" -> "🇨🇿"
            "hungary" -> "🇭🇺"
            "romania" -> "🇷🇴"
            "bulgaria" -> "🇧🇬"
            "croatia" -> "🇭🇷"
            "ukraine" -> "🇺🇦"
            
            // Asia
            "japan" -> "🇯🇵"
            "china" -> "🇨🇳"
            "india" -> "🇮🇳"
            "south korea", "korea" -> "🇰🇷"
            "thailand" -> "🇹🇭"
            "vietnam" -> "🇻🇳"
            "philippines" -> "🇵🇭"
            "indonesia" -> "🇮🇩"
            "malaysia" -> "🇲🇾"
            "singapore" -> "🇸🇬"
            "israel" -> "🇮🇱"
            "turkey" -> "🇹🇷"
            "saudi arabia" -> "🇸🇦"
            "united arab emirates", "uae" -> "🇦🇪"
            "pakistan" -> "🇵🇰"
            "bangladesh" -> "🇧🇩"
            
            // Oceania
            "australia" -> "🇦🇺"
            "new zealand" -> "🇳🇿"
            
            // Africa
            "south africa" -> "🇿🇦"
            "egypt" -> "🇪🇬"
            "nigeria" -> "🇳🇬"
            "kenya" -> "🇰🇪"
            "morocco" -> "🇲🇦"
            
            // Default
            else -> "🌍"
        }
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
        binding.progressBar.visibility = View.VISIBLE
        binding.contentContainer.visibility = View.GONE
        binding.forecastTitle.visibility = View.GONE
        binding.forecastRecyclerView.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
    }

    private fun showWeatherDetail(detail: WeatherDetail) {
        binding.progressBar.visibility = View.GONE
        binding.contentContainer.visibility = View.VISIBLE
        binding.forecastTitle.visibility = View.VISIBLE
        binding.forecastRecyclerView.visibility = View.VISIBLE
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

        // Load weather icon with Coil
        binding.weatherIcon.load(current.conditionIcon) {
            placeholder(android.R.drawable.ic_menu_gallery)
            error(android.R.drawable.ic_dialog_alert)
            crossfade(true)
        }

        // 3-day forecast
        forecastAdapter.submitList(detail.threeDayForecast)
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.contentContainer.visibility = View.GONE
        binding.forecastTitle.visibility = View.GONE
        binding.forecastRecyclerView.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
        binding.errorMessage.text = message
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
