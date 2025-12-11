package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.base.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LocationInfoTest {
    
    @Test
    fun `LocationInfo contains all geographic data`() {
        val locationInfo = TestData.createLocationInfo()
        
        assertThat(locationInfo.name).isEqualTo("Bogota")
        assertThat(locationInfo.region).isEqualTo("Bogota D.C.")
        assertThat(locationInfo.country).isEqualTo("Colombia")
        assertThat(locationInfo.latitude).isEqualTo(4.61)
        assertThat(locationInfo.longitude).isEqualTo(-74.08)
    }
    
    @Test
    fun `LocationInfo contains timezone information`() {
        val locationInfo = TestData.createLocationInfo(
            timezoneId = "America/New_York",
            localtime = "2025-12-11 08:00"
        )
        
        assertThat(locationInfo.timezoneId).isEqualTo("America/New_York")
        assertThat(locationInfo.localtime).isEqualTo("2025-12-11 08:00")
    }
    
    @Test
    fun `LocationInfo with different cities`() {
        val bogota = TestData.createLocationInfo(name = "Bogota", country = "Colombia")
        val london = TestData.createLocationInfo(name = "London", country = "United Kingdom")
        val tokyo = TestData.createLocationInfo(name = "Tokyo", country = "Japan")
        
        assertThat(bogota.name).isEqualTo("Bogota")
        assertThat(london.name).isEqualTo("London")
        assertThat(tokyo.name).isEqualTo("Tokyo")
    }
    
    @Test
    fun `LocationInfo coordinates are valid`() {
        val locationInfo = TestData.createLocationInfo(
            latitude = 40.7128,
            longitude = -74.0060
        )
        
        assertThat(locationInfo.latitude).isWithin(0.0001).of(40.7128)
        assertThat(locationInfo.longitude).isWithin(0.0001).of(-74.0060)
    }
}
