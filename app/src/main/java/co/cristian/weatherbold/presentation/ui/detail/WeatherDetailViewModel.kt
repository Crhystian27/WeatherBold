package co.cristian.weatherbold.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.domain.model.WeatherDetail
import co.cristian.weatherbold.domain.usecase.GetWeatherDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val getWeatherDetailUseCase: GetWeatherDetailUseCase
) : ViewModel() {
    
    // StateFlow para el detalle del clima
    private val _weatherDetail = MutableStateFlow<NetworkResult<WeatherDetail>>(NetworkResult.Loading)
    val weatherDetail: StateFlow<NetworkResult<WeatherDetail>> = _weatherDetail.asStateFlow()
    
    // Ubicación actual
    private val _currentLocation = MutableStateFlow<String?>(null)
    val currentLocation: StateFlow<String?> = _currentLocation.asStateFlow()

    fun getWeatherDetail(
        location: String,
        days: Int = 3
    ) {
        _currentLocation.value = location
        
        viewModelScope.launch {
            getWeatherDetailUseCase(
                location = location,
                days = days
            ).collect { result ->
                _weatherDetail.value = result
            }
        }
    }
    
    /**
     * Reintenta obtener el detalle
     */
    fun retry() {
        _currentLocation.value?.let { location ->
            getWeatherDetail(location)
        }
    }
    
    /**
     * Limpia el estado
     */
    fun clearState() {
        _weatherDetail.value = NetworkResult.Loading
        _currentLocation.value = null
    }
}
