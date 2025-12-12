package co.cristian.weatherbold.domain.model


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
     */
    fun getCountryEmoji(): String {
        return when (country.lowercase().trim()) {
            // Latin America
            "colombia" -> "🇨🇴"
            "mexico" -> "🇲🇽"
            "argentina" -> "🇦🇷"
            "brazil" -> "🇧🇷"
            "chile" -> "🇨🇱"
            "peru" -> "🇵🇪"
            "venezuela" -> "🇻🇪"
            "ecuador" -> "🇪🇨"
            "bolivia" -> "🇧🇴"
            "paraguay" -> "🇵🇾"
            "uruguay" -> "🇺🇾"
            "costa rica" -> "🇨🇷"
            "panama" -> "🇵🇦"
            "guatemala" -> "🇬🇹"
            "honduras" -> "🇭🇳"
            "el salvador" -> "🇸🇻"
            "nicaragua" -> "🇳🇮"
            "cuba" -> "🇨🇺"
            "dominican republic" -> "🇩🇴"
            "puerto rico" -> "🇵🇷"
            
            // North America
            "united states", "usa", "united states of america" -> "🇺🇸"
            "canada" -> "🇨🇦"
            
            // Europe
            "spain", "españa" -> "🇪🇸"
            "united kingdom", "uk", "england", "scotland", "wales" -> "🇬🇧"
            "france" -> "🇫🇷"
            "germany" -> "🇩🇪"
            "italy" -> "🇮🇹"
            "portugal" -> "🇵🇹"
            "netherlands" -> "🇳🇱"
            "belgium" -> "🇧🇪"
            "switzerland" -> "🇨🇭"
            "austria" -> "🇦🇹"
            "sweden" -> "🇸🇪"
            "norway" -> "🇳🇴"
            "denmark" -> "🇩🇰"
            "finland" -> "🇫🇮"
            "poland" -> "🇵🇱"
            "russia", "russian federation" -> "🇷🇺"
            "greece" -> "🇬🇷"
            "ireland" -> "🇮🇪"
            "czech republic", "czechia" -> "🇨🇿"
            "hungary" -> "🇭🇺"
            "romania" -> "🇷🇴"
            "bulgaria" -> "🇧🇬"
            "croatia" -> "🇭🇷"
            "ukraine" -> "🇺🇦"
            
            // Asia
            "japan" -> "🇯🇵"
            "china" -> "🇨🇳"
            "india" -> "🇮🇳"
            "south korea", "korea" -> "🇰🇷"
            "thailand" -> "🇹🇭"
            "vietnam" -> "🇻🇳"
            "philippines" -> "🇵🇭"
            "indonesia" -> "🇮🇩"
            "malaysia" -> "🇲🇾"
            "singapore" -> "🇸🇬"
            "israel" -> "🇮🇱"
            "turkey" -> "🇹🇷"
            "saudi arabia" -> "🇸🇦"
            "united arab emirates", "uae" -> "🇦🇪"
            "pakistan" -> "🇵🇰"
            "bangladesh" -> "🇧🇩"
            
            // Oceania
            "australia" -> "🇦🇺"
            "new zealand" -> "🇳🇿"
            
            // Africa
            "south africa" -> "🇿🇦"
            "egypt" -> "🇪🇬"
            "nigeria" -> "🇳🇬"
            "kenya" -> "🇰🇪"
            "morocco" -> "🇲🇦"
            
            // Default
            else -> "🌍"
        }
    }
}
