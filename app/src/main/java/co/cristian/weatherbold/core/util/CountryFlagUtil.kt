package co.cristian.weatherbold.core.util

import co.cristian.weatherbold.core.network.ApiConstants

/**
 * Utility for country flags using ISO 3166-1 alpha-2 codes
 * Maps country names (from WeatherAPI) to ISO codes for flagcdn.com
 */
object CountryFlagUtil {

    /**
     * Get flag URL from CDN
     * @param countryName Country name in English (as returned by WeatherAPI)
     * @param size Image size (use ApiConstants.FlagCdn.Sizes)
     * @param format Image format (use ApiConstants.FlagCdn.Formats)
     * @return Flag URL or null if country not mapped
     */
    fun getCountryFlagUrl(
        countryName: String,
        size: String = ApiConstants.FlagCdn.Sizes.NORMAL,
        format: String = ApiConstants.FlagCdn.Formats.PNG
    ): String? {
        val isoCode = getCountryIsoCode(countryName) ?: return null
        return ApiConstants.FlagCdn.buildFlagUrl(isoCode, size, format)
    }

    /**
     * Get ISO 3166-1 alpha-2 code for a country
     * @param countryName Country name
     * @return ISO code (e.g. "CO", "US", "MX") or null
     */
    fun getCountryIsoCode(countryName: String): String? {
        val normalized = countryName.lowercase().trim()

        return when {
            // Exact match first
            normalized in countryMap -> countryMap[normalized]
            
            // Partial match (contains)
            else -> countryMap.entries.firstOrNull { (key, _) ->
                normalized.contains(key) || key.contains(normalized)
            }?.value
        }
    }

    /**
     * Country to ISO 3166-1 alpha-2 code map
     * English names as returned by WeatherAPI
     */
    private val countryMap = mapOf(
        // Latin America
        "colombia" to "CO",
        "mexico" to "MX",
        "argentina" to "AR",
        "brazil" to "BR",
        "chile" to "CL",
        "peru" to "PE",
        "venezuela" to "VE",
        "ecuador" to "EC",
        "bolivia" to "BO",
        "paraguay" to "PY",
        "uruguay" to "UY",
        "costa rica" to "CR",
        "panama" to "PA",
        "guatemala" to "GT",
        "honduras" to "HN",
        "el salvador" to "SV",
        "nicaragua" to "NI",
        "cuba" to "CU",
        "dominican republic" to "DO",
        "puerto rico" to "PR",
        "jamaica" to "JM",
        "haiti" to "HT",
        "trinidad and tobago" to "TT",
        "belize" to "BZ",
        "guyana" to "GY",
        "suriname" to "SR",
        
        // North America
        "united states" to "US",
        "usa" to "US",
        "united states of america" to "US",
        "canada" to "CA",
        
        // Europe
        "spain" to "ES",
        "united kingdom" to "GB",
        "uk" to "GB",
        "england" to "GB",
        "scotland" to "GB",
        "wales" to "GB",
        "northern ireland" to "GB",
        "france" to "FR",
        "germany" to "DE",
        "italy" to "IT",
        "portugal" to "PT",
        "netherlands" to "NL",
        "belgium" to "BE",
        "switzerland" to "CH",
        "austria" to "AT",
        "sweden" to "SE",
        "norway" to "NO",
        "denmark" to "DK",
        "finland" to "FI",
        "poland" to "PL",
        "russia" to "RU",
        "russian federation" to "RU",
        "greece" to "GR",
        "ireland" to "IE",
        "czech republic" to "CZ",
        "czechia" to "CZ",
        "hungary" to "HU",
        "romania" to "RO",
        "bulgaria" to "BG",
        "croatia" to "HR",
        "serbia" to "RS",
        "ukraine" to "UA",
        "slovakia" to "SK",
        "slovenia" to "SI",
        "lithuania" to "LT",
        "latvia" to "LV",
        "estonia" to "EE",
        "iceland" to "IS",
        "luxembourg" to "LU",
        "malta" to "MT",
        "cyprus" to "CY",
        "albania" to "AL",
        "bosnia and herzegovina" to "BA",
        "north macedonia" to "MK",
        "macedonia" to "MK",
        "montenegro" to "ME",
        "kosovo" to "XK",
        "moldova" to "MD",
        "belarus" to "BY",
        
        // Asia
        "japan" to "JP",
        "china" to "CN",
        "india" to "IN",
        "south korea" to "KR",
        "korea" to "KR",
        "north korea" to "KP",
        "thailand" to "TH",
        "vietnam" to "VN",
        "philippines" to "PH",
        "indonesia" to "ID",
        "malaysia" to "MY",
        "singapore" to "SG",
        "israel" to "IL",
        "turkey" to "TR",
        "saudi arabia" to "SA",
        "united arab emirates" to "AE",
        "uae" to "AE",
        "pakistan" to "PK",
        "bangladesh" to "BD",
        "taiwan" to "TW",
        "hong kong" to "HK",
        "myanmar" to "MM",
        "cambodia" to "KH",
        "laos" to "LA",
        "nepal" to "NP",
        "sri lanka" to "LK",
        "afghanistan" to "AF",
        "iran" to "IR",
        "iraq" to "IQ",
        "jordan" to "JO",
        "lebanon" to "LB",
        "syria" to "SY",
        "yemen" to "YE",
        "oman" to "OM",
        "kuwait" to "KW",
        "bahrain" to "BH",
        "qatar" to "QA",
        "mongolia" to "MN",
        "kazakhstan" to "KZ",
        "uzbekistan" to "UZ",
        "turkmenistan" to "TM",
        "kyrgyzstan" to "KG",
        "tajikistan" to "TJ",
        "azerbaijan" to "AZ",
        "armenia" to "AM",
        "georgia" to "GE",
        "macao" to "MO",
        "macau" to "MO",
        "brunei" to "BN",
        "maldives" to "MV",
        "bhutan" to "BT",
        "timor-leste" to "TL",
        "east timor" to "TL",
        
        // Oceania
        "australia" to "AU",
        "new zealand" to "NZ",
        "fiji" to "FJ",
        "papua new guinea" to "PG",
        "samoa" to "WS",
        "tonga" to "TO",
        "vanuatu" to "VU",
        "solomon islands" to "SB",
        "micronesia" to "FM",
        "palau" to "PW",
        "marshall islands" to "MH",
        "kiribati" to "KI",
        "tuvalu" to "TV",
        "nauru" to "NR",
        
        // Africa
        "south africa" to "ZA",
        "egypt" to "EG",
        "nigeria" to "NG",
        "kenya" to "KE",
        "morocco" to "MA",
        "algeria" to "DZ",
        "tunisia" to "TN",
        "ethiopia" to "ET",
        "ghana" to "GH",
        "tanzania" to "TZ",
        "uganda" to "UG",
        "zimbabwe" to "ZW",
        "mozambique" to "MZ",
        "cameroon" to "CM",
        "ivory coast" to "CI",
        "cote d'ivoire" to "CI",
        "senegal" to "SN",
        "mali" to "ML",
        "burkina faso" to "BF",
        "niger" to "NE",
        "chad" to "TD",
        "sudan" to "SD",
        "south sudan" to "SS",
        "libya" to "LY",
        "mauritania" to "MR",
        "madagascar" to "MG",
        "angola" to "AO",
        "zambia" to "ZM",
        "malawi" to "MW",
        "botswana" to "BW",
        "namibia" to "NA",
        "rwanda" to "RW",
        "burundi" to "BI",
        "somalia" to "SO",
        "eritrea" to "ER",
        "djibouti" to "DJ",
        "gabon" to "GA",
        "congo" to "CG",
        "democratic republic of the congo" to "CD",
        "dr congo" to "CD",
        "central african republic" to "CF",
        "equatorial guinea" to "GQ",
        "mauritius" to "MU",
        "seychelles" to "SC",
        "comoros" to "KM",
        "cape verde" to "CV",
        "sao tome and principe" to "ST",
        "guinea" to "GN",
        "guinea-bissau" to "GW",
        "sierra leone" to "SL",
        "liberia" to "LR",
        "gambia" to "GM",
        "benin" to "BJ",
        "togo" to "TG",
        "lesotho" to "LS",
        "swaziland" to "SZ",
        "eswatini" to "SZ"
    )
}
