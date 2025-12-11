package co.cristian.weatherbold.data.remote.dto

import com.google.gson.annotations.SerializedName


data class ForecastResponseDto(
    @SerializedName("location")
    val location: LocationInfoDto,
    
    @SerializedName("current")
    val current: CurrentWeatherDto,
    
    @SerializedName("forecast")
    val forecast: ForecastDataDto
)

data class LocationInfoDto(
    @SerializedName("name")
    val name: String,
    
    @SerializedName("region")
    val region: String,
    
    @SerializedName("country")
    val country: String,
    
    @SerializedName("lat")
    val latitude: Double,
    
    @SerializedName("lon")
    val longitude: Double,
    
    @SerializedName("tz_id")
    val timezoneId: String,
    
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long,
    
    @SerializedName("localtime")
    val localtime: String
)

data class CurrentWeatherDto(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Long,
    
    @SerializedName("last_updated")
    val lastUpdated: String,
    
    @SerializedName("temp_c")
    val tempCelsius: Double,
    
    @SerializedName("temp_f")
    val tempFahrenheit: Double,
    
    @SerializedName("is_day")
    val isDay: Int,
    
    @SerializedName("condition")
    val condition: ConditionDto,
    
    @SerializedName("wind_mph")
    val windMph: Double,
    
    @SerializedName("wind_kph")
    val windKph: Double,
    
    @SerializedName("wind_degree")
    val windDegree: Int,
    
    @SerializedName("wind_dir")
    val windDirection: String,
    
    @SerializedName("pressure_mb")
    val pressureMb: Double,
    
    @SerializedName("pressure_in")
    val pressureIn: Double,
    
    @SerializedName("precip_mm")
    val precipMm: Double,
    
    @SerializedName("precip_in")
    val precipIn: Double,
    
    @SerializedName("humidity")
    val humidity: Int,
    
    @SerializedName("cloud")
    val cloud: Int,
    
    @SerializedName("feelslike_c")
    val feelslikeCelsius: Double,
    
    @SerializedName("feelslike_f")
    val feelslikeFahrenheit: Double,
    
    @SerializedName("vis_km")
    val visibilityKm: Double,
    
    @SerializedName("vis_miles")
    val visibilityMiles: Double,
    
    @SerializedName("uv")
    val uvIndex: Double,
    
    @SerializedName("gust_mph")
    val gustMph: Double,
    
    @SerializedName("gust_kph")
    val gustKph: Double
)

data class ConditionDto(
    @SerializedName("text")
    val text: String,
    
    @SerializedName("icon")
    val icon: String,
    
    @SerializedName("code")
    val code: Int
)

data class ForecastDataDto(
    @SerializedName("forecastday")
    val forecastDays: List<ForecastDayDto>
)

data class ForecastDayDto(
    @SerializedName("date")
    val date: String,
    
    @SerializedName("date_epoch")
    val dateEpoch: Long,
    
    @SerializedName("day")
    val day: DayDto,
    
    @SerializedName("astro")
    val astro: AstroDto,
    
    @SerializedName("hour")
    val hours: List<HourDto>
)

data class DayDto(
    @SerializedName("maxtemp_c")
    val maxTempCelsius: Double,
    
    @SerializedName("maxtemp_f")
    val maxTempFahrenheit: Double,
    
    @SerializedName("mintemp_c")
    val minTempCelsius: Double,
    
    @SerializedName("mintemp_f")
    val minTempFahrenheit: Double,
    
    @SerializedName("avgtemp_c")
    val avgTempCelsius: Double,
    
    @SerializedName("avgtemp_f")
    val avgTempFahrenheit: Double,
    
    @SerializedName("maxwind_mph")
    val maxWindMph: Double,
    
    @SerializedName("maxwind_kph")
    val maxWindKph: Double,
    
    @SerializedName("totalprecip_mm")
    val totalPrecipMm: Double,
    
    @SerializedName("totalprecip_in")
    val totalPrecipIn: Double,
    
    @SerializedName("avgvis_km")
    val avgVisibilityKm: Double,
    
    @SerializedName("avgvis_miles")
    val avgVisibilityMiles: Double,
    
    @SerializedName("avghumidity")
    val avgHumidity: Double,
    
    @SerializedName("daily_will_it_rain")
    val dailyWillItRain: Int,
    
    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Int,
    
    @SerializedName("daily_will_it_snow")
    val dailyWillItSnow: Int,
    
    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int,
    
    @SerializedName("condition")
    val condition: ConditionDto,
    
    @SerializedName("uv")
    val uvIndex: Double
)

data class AstroDto(
    @SerializedName("sunrise")
    val sunrise: String,
    
    @SerializedName("sunset")
    val sunset: String,
    
    @SerializedName("moonrise")
    val moonrise: String,
    
    @SerializedName("moonset")
    val moonset: String,
    
    @SerializedName("moon_phase")
    val moonPhase: String,
    
    @SerializedName("moon_illumination")
    val moonIllumination: String
)

data class HourDto(
    @SerializedName("time_epoch")
    val timeEpoch: Long,
    
    @SerializedName("time")
    val time: String,
    
    @SerializedName("temp_c")
    val tempCelsius: Double,
    
    @SerializedName("temp_f")
    val tempFahrenheit: Double,
    
    @SerializedName("is_day")
    val isDay: Int,
    
    @SerializedName("condition")
    val condition: ConditionDto,
    
    @SerializedName("wind_mph")
    val windMph: Double,
    
    @SerializedName("wind_kph")
    val windKph: Double,
    
    @SerializedName("wind_degree")
    val windDegree: Int,
    
    @SerializedName("wind_dir")
    val windDirection: String,
    
    @SerializedName("pressure_mb")
    val pressureMb: Double,
    
    @SerializedName("pressure_in")
    val pressureIn: Double,
    
    @SerializedName("precip_mm")
    val precipMm: Double,
    
    @SerializedName("precip_in")
    val precipIn: Double,
    
    @SerializedName("humidity")
    val humidity: Int,
    
    @SerializedName("cloud")
    val cloud: Int,
    
    @SerializedName("feelslike_c")
    val feelslikeCelsius: Double,
    
    @SerializedName("feelslike_f")
    val feelslikeFahrenheit: Double,
    
    @SerializedName("windchill_c")
    val windchillCelsius: Double,
    
    @SerializedName("windchill_f")
    val windchillFahrenheit: Double,
    
    @SerializedName("heatindex_c")
    val heatindexCelsius: Double,
    
    @SerializedName("heatindex_f")
    val heatindexFahrenheit: Double,
    
    @SerializedName("dewpoint_c")
    val dewpointCelsius: Double,
    
    @SerializedName("dewpoint_f")
    val dewpointFahrenheit: Double,
    
    @SerializedName("will_it_rain")
    val willItRain: Int,
    
    @SerializedName("chance_of_rain")
    val chanceOfRain: Int,
    
    @SerializedName("will_it_snow")
    val willItSnow: Int,
    
    @SerializedName("chance_of_snow")
    val chanceOfSnow: Int,
    
    @SerializedName("vis_km")
    val visibilityKm: Double,
    
    @SerializedName("vis_miles")
    val visibilityMiles: Double,
    
    @SerializedName("gust_mph")
    val gustMph: Double,
    
    @SerializedName("gust_kph")
    val gustKph: Double,
    
    @SerializedName("uv")
    val uvIndex: Double
)
