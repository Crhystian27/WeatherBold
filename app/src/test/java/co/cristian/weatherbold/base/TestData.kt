package co.cristian.weatherbold.base

import co.cristian.weatherbold.domain.model.*

/**
 * Test data factory
 * Centralized test data creation
 */
object TestData {
    
    fun createLocation(
        id: Long = 1,
        name: String = "Bogota",
        region: String = "Bogota D.C.",
        country: String = "Colombia",
        latitude: Double = 4.61,
        longitude: Double = -74.08
    ): Location = Location(id, name, region, country, latitude, longitude)
    
    fun createLocationList(): List<Location> = listOf(
        createLocation(1, "Bogota", "Bogota D.C.", "Colombia", 4.61, -74.08),
        createLocation(2, "Medellin", "Antioquia", "Colombia", 6.25, -75.56),
        createLocation(3, "Cali", "Valle del Cauca", "Colombia", 3.44, -76.52)
    )
    
    fun createWeatherCondition(
        text: String = "Partly Cloudy",
        icon: String = "//cdn.weatherapi.com/weather/64x64/day/116.png",
        code: Int = 1003
    ): WeatherCondition = WeatherCondition(text, icon, code)
    
    fun createCurrentCondition(
        conditionText: String = "Partly Cloudy",
        conditionIcon: String = "//cdn.weatherapi.com/weather/64x64/day/116.png",
        tempCelsius: Double = 12.0,
        feelsLikeCelsius: Double = 9.0,
        windKph: Double = 4.0,
        windDirection: String = "ESE",
        humidity: Int = 100,
        visibilityKm: Double = 10.0
    ): CurrentCondition = CurrentCondition(
        conditionText,
        conditionIcon,
        tempCelsius,
        feelsLikeCelsius,
        windKph,
        windDirection,
        humidity,
        visibilityKm
    )
    
    fun createDayForecastWithName(
        date: String = "2025-12-11",
        dayName: String = "Hoy",
        conditionText: String = "Partly Cloudy",
        conditionIcon: String = "//cdn.weatherapi.com/weather/64x64/day/116.png",
        avgTempCelsius: Double = 12.0
    ): DayForecastWithName = DayForecastWithName(date, dayName, conditionText, conditionIcon, avgTempCelsius)
    
    fun createWeatherDetail(
        locationName: String = "Bogota, Colombia",
        currentWeather: CurrentCondition = createCurrentCondition(),
        threeDayForecast: List<DayForecastWithName> = listOf(
            createDayForecastWithName(dayName = ""),
            createDayForecastWithName(date = "2025-12-12", dayName = ""),
            createDayForecastWithName(date = "2025-12-13", dayName = "")
        )
    ): WeatherDetail = WeatherDetail(locationName, currentWeather, threeDayForecast)
    
    fun createDayForecast(
        date: String = "2025-12-11",
        conditionText: String = "Partly Cloudy",
        conditionIcon: String = "//cdn.weatherapi.com/weather/64x64/day/116.png",
        avgTempCelsius: Double = 12.0
    ): DayForecast = DayForecast(date, conditionText, conditionIcon, avgTempCelsius)
    
    fun createWeatherSummary(
        locationName: String = "Bogota, Colombia",
        forecastDays: List<DayForecast> = listOf(
            createDayForecast(),
            createDayForecast(date = "2025-12-12"),
            createDayForecast(date = "2025-12-13")
        )
    ): WeatherSummary = WeatherSummary(locationName, forecastDays)
    
    fun createLocationInfo(
        name: String = "Bogota",
        region: String = "Bogota D.C.",
        country: String = "Colombia",
        latitude: Double = 4.61,
        longitude: Double = -74.08,
        localtime: String = "2025-12-11 10:00",
        timezoneId: String = "America/Bogota"
    ): LocationInfo = LocationInfo(name, region, country, latitude, longitude, localtime, timezoneId)
    
    fun createCurrentWeather(
        tempCelsius: Double = 12.0,
        tempFahrenheit: Double = 53.6,
        condition: WeatherCondition = createWeatherCondition(),
        windKph: Double = 4.0,
        windDirection: String = "ESE",
        humidity: Int = 100,
        feelslikeCelsius: Double = 9.0,
        feelslikeFahrenheit: Double = 48.2,
        uvIndex: Double = 3.0,
        visibilityKm: Double = 10.0,
        precipMm: Double = 0.0,
        pressureMb: Double = 1013.0,
        cloud: Int = 75,
        isDay: Boolean = true,
        lastUpdated: String = "2025-12-11 10:00"
    ): CurrentWeather = CurrentWeather(
        tempCelsius, tempFahrenheit, condition, windKph, windDirection,
        humidity, feelslikeCelsius, feelslikeFahrenheit, uvIndex,
        visibilityKm, precipMm, pressureMb, cloud, isDay, lastUpdated
    )
    
    fun createForecastDay(
        date: String = "2025-12-11",
        dateEpoch: Long = 1702252800,
        maxTempCelsius: Double = 15.0,
        minTempCelsius: Double = 8.0,
        avgTempCelsius: Double = 12.0,
        maxTempFahrenheit: Double = 59.0,
        minTempFahrenheit: Double = 46.4,
        avgTempFahrenheit: Double = 53.6,
        condition: WeatherCondition = createWeatherCondition(),
        maxWindKph: Double = 10.0,
        totalPrecipMm: Double = 0.0,
        avgHumidity: Double = 85.0,
        chanceOfRain: Int = 20,
        chanceOfSnow: Int = 0,
        uvIndex: Double = 3.0,
        sunrise: String = "06:00 AM",
        sunset: String = "06:00 PM",
        moonPhase: String = "Waxing Crescent",
        hourlyForecast: List<HourlyWeather> = emptyList()
    ): ForecastDay = ForecastDay(
        date, dateEpoch, maxTempCelsius, minTempCelsius, avgTempCelsius,
        maxTempFahrenheit, minTempFahrenheit, avgTempFahrenheit,
        condition, maxWindKph, totalPrecipMm, avgHumidity,
        chanceOfRain, chanceOfSnow, uvIndex, sunrise, sunset, moonPhase, hourlyForecast
    )
    
    fun createWeather(
        location: LocationInfo = createLocationInfo(),
        current: CurrentWeather = createCurrentWeather(),
        forecast: List<ForecastDay> = listOf(
            createForecastDay(),
            createForecastDay(date = "2025-12-12", dateEpoch = 1702339200),
            createForecastDay(date = "2025-12-13", dateEpoch = 1702425600)
        )
    ): Weather = Weather(location, current, forecast)
}
