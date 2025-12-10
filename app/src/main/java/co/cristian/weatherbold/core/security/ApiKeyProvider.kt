package co.cristian.weatherbold.core.security

import co.cristian.weatherbold.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyProvider @Inject constructor() {

    /**
     * The key is read from BuildConfig, generated at compile time
     * from local.properties (not the GitHub version)
     */
    fun getApiKey(): String {
        return BuildConfig.WEATHER_API_KEY
    }
}
