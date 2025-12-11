package co.cristian.weatherbold.di

import co.cristian.weatherbold.data.repository.WeatherRepositoryImpl
import co.cristian.weatherbold.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for repositories
 * Binds interfaces to implementations
 *
 */
@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    /**
     * Bind WeatherRepository interface to implementation
     * Enables dependency inversion and easier testing
     * 
     * Used by:
     * - SearchLocationUseCase
     * - GetForecastUseCase
     * - GetForecastSummaryUseCase
     * - GetWeatherDetailUseCase
     */
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
