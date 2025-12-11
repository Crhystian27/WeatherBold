package co.cristian.weatherbold.domain.usecase

import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.domain.model.Location
import co.cristian.weatherbold.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchLocationUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(query: String): Flow<NetworkResult<List<Location>>> {
        return weatherRepository.searchLocation(query)
    }
}
