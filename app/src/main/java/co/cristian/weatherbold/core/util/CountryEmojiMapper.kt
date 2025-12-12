package co.cristian.weatherbold.core.util

/**
 * Utility object for mapping country names to their flag emojis
 * Centralizes emoji mapping logic to avoid code duplication
 */
object CountryEmojiMapper {
    
    private val countryEmojiMap = mapOf(
        // Latin America
        "colombia" to "🇨🇴",
        "mexico" to "🇲🇽",
        "argentina" to "🇦🇷",
        "brazil" to "🇧🇷",
        "chile" to "🇨🇱",
        "peru" to "🇵🇪",
        "venezuela" to "🇻🇪",
        "ecuador" to "🇪🇨",
        "bolivia" to "🇧🇴",
        "paraguay" to "🇵🇾",
        "uruguay" to "🇺🇾",
        "costa rica" to "🇨🇷",
        "panama" to "🇵🇦",
        "guatemala" to "🇬🇹",
        "honduras" to "🇭🇳",
        "el salvador" to "🇸🇻",
        "nicaragua" to "🇳🇮",
        "cuba" to "🇨🇺",
        "dominican republic" to "🇩🇴",
        "puerto rico" to "🇵🇷",
        
        // North America
        "united states" to "🇺🇸",
        "usa" to "🇺🇸",
        "united states of america" to "🇺🇸",
        "canada" to "🇨🇦",
        
        // Europe
        "spain" to "🇪🇸",
        "españa" to "🇪🇸",
        "united kingdom" to "🇬🇧",
        "uk" to "🇬🇧",
        "england" to "🇬🇧",
        "scotland" to "🇬🇧",
        "wales" to "🇬🇧",
        "france" to "🇫🇷",
        "germany" to "🇩🇪",
        "italy" to "🇮🇹",
        "portugal" to "🇵🇹",
        "netherlands" to "🇳🇱",
        "belgium" to "🇧🇪",
        "switzerland" to "🇨🇭",
        "austria" to "🇦🇹",
        "sweden" to "🇸🇪",
        "norway" to "🇳🇴",
        "denmark" to "🇩🇰",
        "finland" to "🇫🇮",
        "poland" to "🇵🇱",
        "russia" to "🇷🇺",
        "russian federation" to "🇷🇺",
        "greece" to "🇬🇷",
        "ireland" to "🇮🇪",
        "czech republic" to "🇨🇿",
        "czechia" to "🇨🇿",
        "hungary" to "🇭🇺",
        "romania" to "🇷🇴",
        "bulgaria" to "🇧🇬",
        "croatia" to "🇭🇷",
        "ukraine" to "🇺🇦",
        
        // Asia
        "japan" to "🇯🇵",
        "china" to "🇨🇳",
        "india" to "🇮🇳",
        "south korea" to "🇰🇷",
        "korea" to "🇰🇷",
        "thailand" to "🇹🇭",
        "vietnam" to "🇻🇳",
        "philippines" to "🇵🇭",
        "indonesia" to "🇮🇩",
        "malaysia" to "🇲🇾",
        "singapore" to "🇸🇬",
        "israel" to "🇮🇱",
        "turkey" to "🇹🇷",
        "saudi arabia" to "🇸🇦",
        "united arab emirates" to "🇦🇪",
        "uae" to "🇦🇪",
        "pakistan" to "🇵🇰",
        "bangladesh" to "🇧🇩",
        
        // Oceania
        "australia" to "🇦🇺",
        "new zealand" to "🇳🇿",
        
        // Africa
        "south africa" to "🇿🇦",
        "egypt" to "🇪🇬",
        "nigeria" to "🇳🇬",
        "kenya" to "🇰🇪",
        "morocco" to "🇲🇦"
    )
    
    /**
     * Returns the flag emoji for a given country name
     * @param country Country name (case-insensitive)
     * @return Flag emoji or 🌍 if country not found
     */
    fun getEmojiForCountry(country: String): String {
        return countryEmojiMap[country.lowercase().trim()] ?: DEFAULT_EMOJI
    }
    
    private const val DEFAULT_EMOJI = "🌍"
}
