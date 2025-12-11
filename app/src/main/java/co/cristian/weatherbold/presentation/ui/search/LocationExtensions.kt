package co.cristian.weatherbold.presentation.ui.search

import co.cristian.weatherbold.core.network.ApiConstants
import co.cristian.weatherbold.core.util.CountryFlagUtil
import co.cristian.weatherbold.domain.model.Location

/**
 * Get country flag URL from CDN
 * @param size Image size (use ApiConstants.FlagCdn.Sizes)
 * @return Flag URL or null if country not mapped
 */
fun Location.getCountryFlagUrl(
    size: String = ApiConstants.FlagCdn.Sizes.NORMAL
): String? {
    return CountryFlagUtil.getCountryFlagUrl(country, size)
}
