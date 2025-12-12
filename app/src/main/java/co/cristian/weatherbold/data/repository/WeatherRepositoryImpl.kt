package co.cristian.weatherbold.data.repository

import co.cristian.weatherbold.core.network.NetworkConnectivityManager
import co.cristian.weatherbold.core.network.NetworkErrorHandler
import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.data.mapper.WeatherMapper
import co.cristian.weatherbold.data.remote.datasource.WeatherRemoteDataSource
import co.cristian.weatherbold.domain.model.Location
import co.cristian.weatherbold.domain.model.WeatherDetail
import co.cristian.weatherbold.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Weather repository implementation coordinating data sources and network connectivity
 */
class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val weatherMapper: WeatherMapper,
    private val networkConnectivityManager: NetworkConnectivityManager,
    private val networkErrorHandler: NetworkErrorHandler
) : WeatherRepository {
    
    override fun searchLocation(query: String): Flow<NetworkResult<List<Location>>> = flow {
        emit(NetworkResult.Loading)
        
        // Check connectivity before request
        if (!networkConnectivityManager.isNetworkAvailable()) {
            emit(NetworkResult.Error(
                message = "No internet connection. Please check your connection.",
                code = null
            ))
            return@flow
        }
        
        try {
            val response = remoteDataSource.searchLocation(query)
            val locations = weatherMapper.mapLocationDtoListToDomain(response)
            emit(NetworkResult.Success(locations))
        } catch (e: Exception) {
            emit(networkErrorHandler.handleError(e))
        }
    }.flowOn(Dispatchers.IO)
    
    /**
     * Gets complete weather detail (current weather + 3-day forecast)
     */
    override fun getWeatherDetail(
        location: String,
        days: Int
    ): Flow<NetworkResult<WeatherDetail>> = flow {
        emit(NetworkResult.Loading)
        
        // Check connectivity before request
        if (!networkConnectivityManager.isNetworkAvailable()) {
            emit(NetworkResult.Error(
                message = "No internet connection. Please check your connection.",
                code = null
            ))
            return@flow
        }
        
        try {
            val response = remoteDataSource.getForecast(
                location = location,
                days = days
            )
            val detail = weatherMapper.mapForecastResponseToDetail(response)
            emit(NetworkResult.Success(detail))
        } catch (e: Exception) {
            emit(networkErrorHandler.handleError(e))
        }
    }.flowOn(Dispatchers.IO)
}
