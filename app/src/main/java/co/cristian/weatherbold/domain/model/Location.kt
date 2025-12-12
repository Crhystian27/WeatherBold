package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.core.util.CountryEmojiMapper

data class Location(
    val id: Long,
    val name: String,
    val region: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
) {

    val displayName: String
        get() = "$name, $region\n$country"
    
    /**
     * Returns country emoji flag based on country name
     * Uses CountryEmojiMapper for centralized emoji mapping
     */
    fun getCountryEmoji(): String {
        return CountryEmojiMapper.getEmojiForCountry(country)
    }
}
