package co.cristian.weatherbold.core.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CountryFlagUtilTest {
    
    @Test
    fun `get country iso code for Colombia returns CO`() {
        val code = CountryFlagUtil.getCountryIsoCode("Colombia")
        
        assertThat(code).isEqualTo("CO")
    }
    
    @Test
    fun `get country iso code for United States returns US`() {
        val code = CountryFlagUtil.getCountryIsoCode("United States")
        
        assertThat(code).isEqualTo("US")
    }
    
    @Test
    fun `get country iso code for unknown country returns null`() {
        val code = CountryFlagUtil.getCountryIsoCode("Unknown Country XYZ 123")
        
        assertThat(code).isNull()
    }
    
    @Test
    fun `get country iso code is case insensitive`() {
        val code1 = CountryFlagUtil.getCountryIsoCode("COLOMBIA")
        val code2 = CountryFlagUtil.getCountryIsoCode("colombia")
        val code3 = CountryFlagUtil.getCountryIsoCode("Colombia")
        
        assertThat(code1).isEqualTo("CO")
        assertThat(code2).isEqualTo("CO")
        assertThat(code3).isEqualTo("CO")
    }
    
    @Test
    fun `get country flag url for valid country returns url`() {
        val url = CountryFlagUtil.getCountryFlagUrl("Colombia")
        
        assertThat(url).isNotNull()
        assertThat(url).contains("flagcdn.com")
    }
}
