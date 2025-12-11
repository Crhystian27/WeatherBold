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
}
