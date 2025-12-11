package co.cristian.weatherbold.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("region")
    val region: String,
    
    @SerializedName("country")
    val country: String,
    
    @SerializedName("lat")
    val latitude: Double,
    
    @SerializedName("lon")
    val longitude: Double,
    
    @SerializedName("url")
    val url: String
)
