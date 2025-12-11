package co.cristian.weatherbold.data.remote.api

import co.cristian.weatherbold.core.network.ApiConstants
import co.cristian.weatherbold.data.remote.dto.ForecastResponseDto
import co.cristian.weatherbold.data.remote.dto.LocationDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET(ApiConstants.Endpoints.SEARCH)
    suspend fun searchLocation(
        @Query(ApiConstants.QueryParams.QUERY) query: String
    ): List<LocationDto>

    @GET(ApiConstants.Endpoints.FORECAST)
    suspend fun getForecast(
        @Query(ApiConstants.QueryParams.QUERY) location: String,
        @Query(ApiConstants.QueryParams.DAYS) days: Int = ApiConstants.DEFAULT_FORECAST_DAYS,
    ): ForecastResponseDto
}