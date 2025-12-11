package co.cristian.weatherbold.core.util

import android.content.Context
import co.cristian.weatherbold.R

/**
 * Utility class for formatting weather data using string resources
 */
object WeatherFormatter {
    
    fun formatTemperature(context: Context, tempCelsius: Double): String {
        return context.getString(R.string.format_temperature, tempCelsius.toInt())
    }
    
    fun formatFeelsLike(context: Context, feelsLikeCelsius: Double): String {
        return context.getString(R.string.format_feels_like, feelsLikeCelsius.toInt())
    }
    
    fun formatWind(context: Context, windKph: Double, direction: String): String {
        return context.getString(R.string.format_wind, windKph.toInt(), direction)
    }
    
    fun formatHumidity(context: Context, humidity: Int): String {
        return context.getString(R.string.format_humidity, humidity)
    }
    
    fun formatVisibility(context: Context, visibilityKm: Double): String {
        return context.getString(R.string.format_visibility, visibilityKm.toInt())
    }
    
    /**
     * Get day name from resources based on index
     * @param context Android context
     * @param dayIndex 0 = Today, 1 = Tomorrow, 2 = Day after tomorrow
     * @return Localized day name
     */
    fun getDayName(context: Context, dayIndex: Int): String {
        return when (dayIndex) {
            0 -> context.getString(R.string.day_today)
            1 -> context.getString(R.string.day_tomorrow)
            2 -> context.getString(R.string.day_after_tomorrow)
            else -> ""
        }
    }
}
