package co.cristian.weatherbold.util

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

/**
 * Utility class for theme and dark mode management
 */
object ThemeUtils {
    
    /**
     * Check if dark mode is currently active
     */
    fun isDarkModeActive(context: Context): Boolean {
        val nightModeFlags = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    fun setNightMode(@AppCompatDelegate.NightMode mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}
