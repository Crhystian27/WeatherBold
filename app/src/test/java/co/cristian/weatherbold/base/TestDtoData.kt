package co.cristian.weatherbold.base

import co.cristian.weatherbold.data.remote.dto.*

/**
 * Simplified test DTO data factory
 * Only creates minimal DTOs needed for testing
 */
object TestDtoData {
    
    fun createLocationDto(
        id: Long = 1,
        name: String = "Bogota",
        region: String = "Bogota D.C.",
        country: String = "Colombia",
        latitude: Double = 4.61,
        longitude: Double = -74.08,
        url: String = "bogota-bogota-d-c-colombia"
    ) = LocationDto(id, name, region, country, latitude, longitude, url)
    
    fun createConditionDto(
        text: String = "Partly Cloudy",
        icon: String = "//cdn.weatherapi.com/weather/64x64/day/116.png",
        code: Int = 1003
    ) = ConditionDto(text, icon, code)
    
    fun createLocationInfoDto(
        name: String = "Bogota",
        region: String = "Bogota D.C.",
        country: String = "Colombia",
        latitude: Double = 4.61,
        longitude: Double = -74.08,
        timezoneId: String = "America/Bogota",
        localtimeEpoch: Long = 1702296000,
        localtime: String = "2025-12-11 10:00"
    ) = LocationInfoDto(name, region, country, latitude, longitude, timezoneId, localtimeEpoch, localtime)
    
    fun createCurrentWeatherDto() = CurrentWeatherDto(
        lastUpdatedEpoch = 1702296000,
        lastUpdated = "2025-12-11 10:00",
        tempCelsius = 12.0,
        tempFahrenheit = 53.6,
        isDay = 1,
        condition = createConditionDto(),
        windMph = 2.5,
        windKph = 4.0,
        windDegree = 112,
        windDirection = "ESE",
        pressureMb = 1013.0,
        pressureIn = 29.91,
        precipMm = 0.0,
        precipIn = 0.0,
        humidity = 100,
        cloud = 75,
        feelslikeCelsius = 9.0,
        feelslikeFahrenheit = 48.2,
        visibilityKm = 10.0,
        visibilityMiles = 6.0,
        uvIndex = 3.0,
        gustMph = 3.0,
        gustKph = 5.0
    )
    
    fun createDayDto() = DayDto(
        maxTempCelsius = 15.0,
        maxTempFahrenheit = 59.0,
        minTempCelsius = 8.0,
        minTempFahrenheit = 46.4,
        avgTempCelsius = 12.0,
        avgTempFahrenheit = 53.6,
        maxWindMph = 6.2,
        maxWindKph = 10.0,
        totalPrecipMm = 0.0,
        totalPrecipIn = 0.0,
        avgVisibilityKm = 10.0,
        avgVisibilityMiles = 6.0,
        avgHumidity = 85.0,
        dailyWillItRain = 0,
        dailyChanceOfRain = 20,
        dailyWillItSnow = 0,
        dailyChanceOfSnow = 0,
        condition = createConditionDto(),
        uvIndex = 3.0
    )
    
    fun createAstroDto() = AstroDto(
        sunrise = "06:00 AM",
        sunset = "06:00 PM",
        moonrise = "07:00 PM",
        moonset = "07:00 AM",
        moonPhase = "Waxing Crescent",
        moonIllumination = "25"
    )
    
    fun createForecastDayDto(
        date: String = "2025-12-11",
        dateEpoch: Long = 1702252800
    ) = ForecastDayDto(
        date = date,
        dateEpoch = dateEpoch,
        day = createDayDto(),
        astro = createAstroDto(),
        hours = emptyList()
    )
    
    fun createForecastResponseDto() = ForecastResponseDto(
        location = createLocationInfoDto(),
        current = createCurrentWeatherDto(),
        forecast = ForecastDataDto(
            listOf(
                createForecastDayDto("2025-12-11", 1702252800),
                createForecastDayDto("2025-12-12", 1702339200),
                createForecastDayDto("2025-12-13", 1702425600)
            )
        )
    )
    
    fun createForecastDto() = createForecastResponseDto()
    
    fun createLocationDtoList() = listOf(
        createLocationDto(1, "Bogota", "Bogota D.C.", "Colombia", 4.61, -74.08),
        createLocationDto(2, "Medellin", "Antioquia", "Colombia", 6.25, -75.56),
        createLocationDto(3, "Cali", "Valle del Cauca", "Colombia", 3.44, -76.52)
    )
}
