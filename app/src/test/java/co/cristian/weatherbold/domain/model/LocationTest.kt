package co.cristian.weatherbold.domain.model

import co.cristian.weatherbold.base.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LocationTest {
    
    @Test
    fun `Location contains all required fields`() {
        val location = TestData.createLocation()
        
        assertThat(location.id).isEqualTo(1)
        assertThat(location.name).isEqualTo("Bogota")
        assertThat(location.region).isEqualTo("Bogota D.C.")
        assertThat(location.country).isEqualTo("Colombia")
        assertThat(location.latitude).isEqualTo(4.61)
        assertThat(location.longitude).isEqualTo(-74.08)
    }
    
    @Test
    fun `Location list contains multiple locations`() {
        val locations = TestData.createLocationList()
        
        assertThat(locations).hasSize(3)
        assertThat(locations[0].name).isEqualTo("Bogota")
        assertThat(locations[1].name).isEqualTo("Medellin")
        assertThat(locations[2].name).isEqualTo("Cali")
    }
}
