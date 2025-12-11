package co.cristian.weatherbold.domain.usecase

import co.cristian.weatherbold.core.network.NetworkResult
import co.cristian.weatherbold.domain.model.WeatherDetail
import co.cristian.weatherbold.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherDetailUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    operator fun invoke(
        location: String,
        days: Int = 3
    ): Flow<NetworkResult<WeatherDetail>> {
        return weatherRepository.getWeatherDetail(
            location = location,
            days = days
        )
    }
}
