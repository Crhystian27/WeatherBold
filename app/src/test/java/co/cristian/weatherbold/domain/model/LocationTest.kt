package co.cristian.weatherbold.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LocationTest {

    @Test
    fun `displayName formats correctly with all fields`() {
        val location = Location(
            id = 1,
            name = "Bogota",
            region = "Bogota D.C.",
            country = "Colombia",
            latitude = 4.61,
            longitude = -74.08
        )

        val result = location.displayName

        assertThat(result).isEqualTo("Bogota, Bogota D.C.\nColombia")
    }

    @Test
    fun `displayName includes newline between region and country`() {
        val location = Location(
            id = 2,
            name = "Medellin",
            region = "Antioquia",
            country = "Colombia",
            latitude = 6.25,
            longitude = -75.56
        )

        assertThat(location.displayName).contains("\n")
        assertThat(location.displayName).startsWith("Medellin, Antioquia")
        assertThat(location.displayName).endsWith("Colombia")
    }

    @Test
    fun `location with empty region still formats correctly`() {
        val location = Location(
            id = 3,
            name = "Cali",
            region = "",
            country = "Colombia",
            latitude = 3.44,
            longitude = -76.52
        )

        val result = location.displayName

        assertThat(result).isEqualTo("Cali, \nColombia")
    }

    @Test
    fun `location equality works correctly`() {
        val location1 = Location(1, "Bogota", "Bogota D.C.", "Colombia", 4.61, -74.08)
        val location2 = Location(1, "Bogota", "Bogota D.C.", "Colombia", 4.61, -74.08)

        assertThat(location1).isEqualTo(location2)
    }

    @Test
    fun `location with different id are not equal`() {
        val location1 = Location(1, "Bogota", "Bogota D.C.", "Colombia", 4.61, -74.08)
        val location2 = Location(2, "Bogota", "Bogota D.C.", "Colombia", 4.61, -74.08)

        assertThat(location1).isNotEqualTo(location2)
    }
}
