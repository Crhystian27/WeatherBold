package co.cristian.weatherbold.domain.repository

import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.domain.model.Location
import co.cristian.weatherbold.domain.model.Weather
import co.cristian.weatherbold.domain.model.WeatherSummary
import co.cristian.weatherbold.domain.model.WeatherDetail
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun searchLocation(query: String): Flow<NetworkResult<List<Location>>>

    fun getForecast(
        location: String,
        days: Int,
        includeAqi: Boolean = false,
        includeAlerts: Boolean = false
    ): Flow<NetworkResult<Weather>>
    
    fun getForecastSummary(
        location: String,
        days: Int = 3
    ): Flow<NetworkResult<WeatherSummary>>
    
    fun getWeatherDetail(
        location: String,
        days: Int = 3
    ): Flow<NetworkResult<WeatherDetail>>
}
