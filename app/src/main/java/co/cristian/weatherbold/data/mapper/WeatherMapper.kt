package co.cristian.weatherbold.data.mapper

import co.cristian.weatherbold.data.remote.dto.*
import co.cristian.weatherbold.domain.model.*
import javax.inject.Inject

/**
 * Mapper for transforming DTOs from API to domain models
 * Handles icon URL formatting (adds https: protocol)
 */
class WeatherMapper @Inject constructor() {

    // Location mapping
    fun mapLocationDtoListToDomain(dtoList: List<LocationDto>): List<Location> =
        dtoList.map(::mapLocationDtoToDomain)

    fun mapLocationDtoToDomain(dto: LocationDto): Location = Location(
        id = dto.id,
        name = dto.name,
        region = dto.region,
        country = dto.country,
        latitude = dto.latitude,
        longitude = dto.longitude
    )

    // Weather mapping
    fun mapForecastResponseToDomain(dto: ForecastResponseDto): Weather = Weather(
        location = mapLocationInfoToDomain(dto.location),
        current = mapCurrentWeatherToDomain(dto.current),
        forecast = dto.forecast.forecastDays.map(::mapForecastDayToDomain)
    )

    fun mapForecastResponseToSummary(dto: ForecastResponseDto): WeatherSummary {
        val locationName = "${dto.location.name}, ${dto.location.country}"
        
        // Take only first 3 days
        val forecastDays = dto.forecast.forecastDays
            .take(3)
            .map { dayDto ->
                DayForecast(
                    date = dayDto.date,
                    conditionText = dayDto.day.condition.text,
                    conditionIcon = addHttpsProtocol(dayDto.day.condition.icon),
                    avgTempCelsius = dayDto.day.avgTempCelsius
                )
            }
        
        return WeatherSummary(
            locationName = locationName,
            forecastDays = forecastDays
        )
    }
    
    private fun mapLocationInfoToDomain(dto: LocationInfoDto): LocationInfo {
        return LocationInfo(
            name = dto.name,
            region = dto.region,
            country = dto.country,
            latitude = dto.latitude,
            longitude = dto.longitude,
            localtime = dto.localtime,
            timezoneId = dto.timezoneId
        )
    }
    
    private fun mapCurrentWeatherToDomain(dto: CurrentWeatherDto): CurrentWeather {
        return CurrentWeather(
            tempCelsius = dto.tempCelsius,
            tempFahrenheit = dto.tempFahrenheit,
            condition = mapConditionToDomain(dto.condition),
            windKph = dto.windKph,
            windDirection = dto.windDirection,
            humidity = dto.humidity,
            feelslikeCelsius = dto.feelslikeCelsius,
            feelslikeFahrenheit = dto.feelslikeFahrenheit,
            uvIndex = dto.uvIndex,
            visibilityKm = dto.visibilityKm,
            precipMm = dto.precipMm,
            pressureMb = dto.pressureMb,
            cloud = dto.cloud,
            isDay = dto.isDay == 1,
            lastUpdated = dto.lastUpdated
        )
    }
    
    private fun mapConditionToDomain(dto: ConditionDto): WeatherCondition = WeatherCondition(
        text = dto.text,
        icon = addHttpsProtocol(dto.icon),
        code = dto.code
    )
    
    /**
     * Adds https: protocol to icon URLs (API returns URLs without protocol)
     */
    private fun addHttpsProtocol(iconUrl: String): String = "https:$iconUrl"
    
    private fun mapForecastDayToDomain(dto: ForecastDayDto): ForecastDay {
        return ForecastDay(
            date = dto.date,
            dateEpoch = dto.dateEpoch,
            maxTempCelsius = dto.day.maxTempCelsius,
            minTempCelsius = dto.day.minTempCelsius,
            avgTempCelsius = dto.day.avgTempCelsius,
            maxTempFahrenheit = dto.day.maxTempFahrenheit,
            minTempFahrenheit = dto.day.minTempFahrenheit,
            avgTempFahrenheit = dto.day.avgTempFahrenheit,
            condition = mapConditionToDomain(dto.day.condition),
            maxWindKph = dto.day.maxWindKph,
            totalPrecipMm = dto.day.totalPrecipMm,
            avgHumidity = dto.day.avgHumidity,
            chanceOfRain = dto.day.dailyChanceOfRain,
            chanceOfSnow = dto.day.dailyChanceOfSnow,
            uvIndex = dto.day.uvIndex,
            sunrise = dto.astro.sunrise,
            sunset = dto.astro.sunset,
            moonPhase = dto.astro.moonPhase,
            hourlyForecast = dto.hours.map { mapHourlyWeatherToDomain(it) }
        )
    }
    
    private fun mapHourlyWeatherToDomain(dto: HourDto): HourlyWeather {
        return HourlyWeather(
            time = dto.time,
            timeEpoch = dto.timeEpoch,
            tempCelsius = dto.tempCelsius,
            tempFahrenheit = dto.tempFahrenheit,
            condition = mapConditionToDomain(dto.condition),
            windKph = dto.windKph,
            humidity = dto.humidity,
            feelslikeCelsius = dto.feelslikeCelsius,
            chanceOfRain = dto.chanceOfRain,
            chanceOfSnow = dto.chanceOfSnow,
            isDay = dto.isDay == 1
        )
    }

    /**
     * Maps forecast response to weather detail for UI display
     * Day names are empty and filled in UI layer using WeatherFormatter for localization
     */
    fun mapForecastResponseToDetail(dto: ForecastResponseDto): WeatherDetail {
        val locationName = "${dto.location.name}, ${dto.location.country}"
        
        val currentCondition = CurrentCondition(
            conditionText = dto.current.condition.text,
            conditionIcon = addHttpsProtocol(dto.current.condition.icon),
            tempCelsius = dto.current.tempCelsius,
            feelsLikeCelsius = dto.current.feelslikeCelsius,
            windKph = dto.current.windKph,
            windDirection = dto.current.windDirection,
            humidity = dto.current.humidity,
            visibilityKm = dto.current.visibilityKm
        )
        
        val threeDayForecast = dto.forecast.forecastDays
            .take(3)
            .map { dayDto ->
                DayForecastWithName(
                    date = dayDto.date,
                    dayName = "", // Filled in UI using WeatherFormatter.getDayName()
                    conditionText = dayDto.day.condition.text,
                    conditionIcon = addHttpsProtocol(dayDto.day.condition.icon),
                    avgTempCelsius = dayDto.day.avgTempCelsius
                )
            }
        
        return WeatherDetail(
            locationName = locationName,
            currentWeather = currentCondition,
            threeDayForecast = threeDayForecast
        )
    }
}
