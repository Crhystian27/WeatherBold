package co.cristian.weatherbold.core.util

import android.content.Context
import co.cristian.weatherbold.R
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class WeatherFormatterTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = mockk(relaxed = true)
    }

    @Test
    fun `formatTemperature returns correct format`() {
        every { context.getString(R.string.format_temperature, 25) } returns "25°C"

        val result = WeatherFormatter.formatTemperature(context, 25.5)

        assertThat(result).isEqualTo("25°C")
    }

    @Test
    fun `formatTemperature rounds decimal correctly`() {
        every { context.getString(R.string.format_temperature, 25) } returns "25°C"

        val result = WeatherFormatter.formatTemperature(context, 25.4)

        assertThat(result).isEqualTo("25°C")
    }

    @Test
    fun `formatTemperature handles negative values`() {
        every { context.getString(R.string.format_temperature, -5) } returns "-5°C"

        val result = WeatherFormatter.formatTemperature(context, -5.3)

        assertThat(result).isEqualTo("-5°C")
    }

    @Test
    fun `formatFeelsLike returns correct format`() {
        every { context.getString(R.string.format_feels_like, 23) } returns "Sensación: 23°C"

        val result = WeatherFormatter.formatFeelsLike(context, 23.4)

        assertThat(result).isEqualTo("Sensación: 23°C")
    }

    @Test
    fun `formatWind returns correct format`() {
        every { context.getString(R.string.format_wind, 15, "N") } returns "15 km/h N"

        val result = WeatherFormatter.formatWind(context, 15.2, "N")

        assertThat(result).isEqualTo("15 km/h N")
    }

    @Test
    fun `formatWind handles different directions`() {
        every { context.getString(R.string.format_wind, 20, "SW") } returns "20 km/h SW"

        val result = WeatherFormatter.formatWind(context, 20.0, "SW")

        assertThat(result).isEqualTo("20 km/h SW")
    }

    @Test
    fun `formatHumidity returns correct format`() {
        every { context.getString(R.string.format_humidity, 65) } returns "65%"

        val result = WeatherFormatter.formatHumidity(context, 65)

        assertThat(result).isEqualTo("65%")
    }

    @Test
    fun `formatHumidity handles edge values`() {
        every { context.getString(R.string.format_humidity, 100) } returns "100%"

        val result = WeatherFormatter.formatHumidity(context, 100)

        assertThat(result).isEqualTo("100%")
    }

    @Test
    fun `formatVisibility returns correct format`() {
        every { context.getString(R.string.format_visibility, 10) } returns "10 km"

        val result = WeatherFormatter.formatVisibility(context, 10.5)

        assertThat(result).isEqualTo("10 km")
    }

    @Test
    fun `formatVisibility rounds correctly`() {
        every { context.getString(R.string.format_visibility, 7) } returns "7 km"

        val result = WeatherFormatter.formatVisibility(context, 7.4)

        assertThat(result).isEqualTo("7 km")
    }

    @Test
    fun `getDayName returns today for index 0`() {
        every { context.getString(R.string.day_today) } returns "Hoy"

        val result = WeatherFormatter.getDayName(context, 0)

        assertThat(result).isEqualTo("Hoy")
    }

    @Test
    fun `getDayName returns tomorrow for index 1`() {
        every { context.getString(R.string.day_tomorrow) } returns "Mañana"

        val result = WeatherFormatter.getDayName(context, 1)

        assertThat(result).isEqualTo("Mañana")
    }

    @Test
    fun `getDayName returns day after tomorrow for index 2`() {
        every { context.getString(R.string.day_after_tomorrow) } returns "Pasado mañana"

        val result = WeatherFormatter.getDayName(context, 2)

        assertThat(result).isEqualTo("Pasado mañana")
    }

    @Test
    fun `getDayName returns empty string for invalid index`() {
        val result = WeatherFormatter.getDayName(context, 3)

        assertThat(result).isEmpty()
    }

    @Test
    fun `getDayName returns empty string for negative index`() {
        val result = WeatherFormatter.getDayName(context, -1)

        assertThat(result).isEmpty()
    }
}
