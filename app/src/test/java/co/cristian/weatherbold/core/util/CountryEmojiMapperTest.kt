package co.cristian.weatherbold.core.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CountryEmojiMapperTest {

    @Test
    fun `getEmojiForCountry returns correct emoji for Colombia`() {
        val result = CountryEmojiMapper.getEmojiForCountry("Colombia")
        assertThat(result).isEqualTo("🇨🇴")
    }

    @Test
    fun `getEmojiForCountry returns correct emoji for Mexico`() {
        val result = CountryEmojiMapper.getEmojiForCountry("Mexico")
        assertThat(result).isEqualTo("🇲🇽")
    }

    @Test
    fun `getEmojiForCountry returns correct emoji for United States`() {
        val result = CountryEmojiMapper.getEmojiForCountry("United States")
        assertThat(result).isEqualTo("🇺🇸")
    }

    @Test
    fun `getEmojiForCountry returns correct emoji for USA`() {
        val result = CountryEmojiMapper.getEmojiForCountry("USA")
        assertThat(result).isEqualTo("🇺🇸")
    }

    @Test
    fun `getEmojiForCountry returns correct emoji for Spain`() {
        val result = CountryEmojiMapper.getEmojiForCountry("Spain")
        assertThat(result).isEqualTo("🇪🇸")
    }

    @Test
    fun `getEmojiForCountry returns correct emoji for España`() {
        val result = CountryEmojiMapper.getEmojiForCountry("España")
        assertThat(result).isEqualTo("🇪🇸")
    }

    @Test
    fun `getEmojiForCountry handles case insensitive input`() {
        assertThat(CountryEmojiMapper.getEmojiForCountry("COLOMBIA")).isEqualTo("🇨🇴")
        assertThat(CountryEmojiMapper.getEmojiForCountry("colombia")).isEqualTo("🇨🇴")
        assertThat(CountryEmojiMapper.getEmojiForCountry("CoLoMbIa")).isEqualTo("🇨🇴")
    }

    @Test
    fun `getEmojiForCountry trims whitespace`() {
        assertThat(CountryEmojiMapper.getEmojiForCountry("  Colombia  ")).isEqualTo("🇨🇴")
        assertThat(CountryEmojiMapper.getEmojiForCountry("\tMexico\t")).isEqualTo("🇲🇽")
    }

    @Test
    fun `getEmojiForCountry returns default emoji for unknown country`() {
        val result = CountryEmojiMapper.getEmojiForCountry("Unknown Country")
        assertThat(result).isEqualTo("🌍")
    }

    @Test
    fun `getEmojiForCountry returns default emoji for empty string`() {
        val result = CountryEmojiMapper.getEmojiForCountry("")
        assertThat(result).isEqualTo("🌍")
    }

    @Test
    fun `getEmojiForCountry returns correct emoji for all Latin American countries`() {
        val testCases = mapOf(
            "Colombia" to "🇨🇴",
            "Mexico" to "🇲🇽",
            "Argentina" to "🇦🇷",
            "Brazil" to "🇧🇷",
            "Chile" to "🇨🇱",
            "Peru" to "🇵🇪",
            "Venezuela" to "🇻🇪",
            "Ecuador" to "🇪🇨"
        )

        testCases.forEach { (country, expectedEmoji) ->
            assertThat(CountryEmojiMapper.getEmojiForCountry(country)).isEqualTo(expectedEmoji)
        }
    }

    @Test
    fun `getEmojiForCountry returns correct emoji for European countries`() {
        val testCases = mapOf(
            "United Kingdom" to "🇬🇧",
            "UK" to "🇬🇧",
            "France" to "🇫🇷",
            "Germany" to "🇩🇪",
            "Italy" to "🇮🇹",
            "Portugal" to "🇵🇹"
        )

        testCases.forEach { (country, expectedEmoji) ->
            assertThat(CountryEmojiMapper.getEmojiForCountry(country)).isEqualTo(expectedEmoji)
        }
    }

    @Test
    fun `getEmojiForCountry returns correct emoji for Asian countries`() {
        val testCases = mapOf(
            "Japan" to "🇯🇵",
            "China" to "🇨🇳",
            "India" to "🇮🇳",
            "South Korea" to "🇰🇷",
            "Thailand" to "🇹🇭"
        )

        testCases.forEach { (country, expectedEmoji) ->
            assertThat(CountryEmojiMapper.getEmojiForCountry(country)).isEqualTo(expectedEmoji)
        }
    }
}
