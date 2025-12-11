package co.cristian.weatherbold.data.remote.datasource

import co.cristian.weatherbold.data.remote.api.WeatherApiService
import co.cristian.weatherbold.data.remote.dto.ForecastResponseDto
import co.cristian.weatherbold.data.remote.dto.LocationDto
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val apiService: WeatherApiService
) {

    suspend fun searchLocation(query: String): List<LocationDto> {
        return apiService.searchLocation(query)
    }

    suspend fun getForecast(
        location: String,
        days: Int
    ): ForecastResponseDto {
        return apiService.getForecast(
            location = location,
            days = days
        )
    }
}
