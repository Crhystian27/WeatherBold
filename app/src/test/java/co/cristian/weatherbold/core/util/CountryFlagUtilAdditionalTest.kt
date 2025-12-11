package co.cristian.weatherbold.core.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Tests adicionales para CountryFlagUtil
 * Enfocados en casos edge y validaciones
 */
class CountryFlagUtilAdditionalTest {
    
    @Test
    fun `getCountryFlagUrl with valid country returns URL`() {
        val result = CountryFlagUtil.getCountryFlagUrl("Colombia")
        
        assertThat(result).isNotNull()
        assertThat(result).contains("flagcdn.com")
    }
    
    @Test
    fun `getCountryFlagUrl with USA returns URL`() {
        val result = CountryFlagUtil.getCountryFlagUrl("USA")
        
        assertThat(result).isNotNull()
        assertThat(result).contains("flagcdn.com")
    }
    
    @Test
    fun `getCountryFlagUrl with United Kingdom returns URL`() {
        val result = CountryFlagUtil.getCountryFlagUrl("United Kingdom")
        
        assertThat(result).isNotNull()
        assertThat(result).contains("flagcdn.com")
    }
    
    @Test
    fun `getCountryFlagUrl with Mexico returns URL`() {
        val result = CountryFlagUtil.getCountryFlagUrl("Mexico")
        
        assertThat(result).isNotNull()
        assertThat(result).contains("flagcdn.com")
    }
    
    @Test
    fun `getCountryIsoCode with Colombia returns CO`() {
        val result = CountryFlagUtil.getCountryIsoCode("Colombia")
        
        assertThat(result).isEqualTo("CO")
    }
    
    @Test
    fun `getCountryIsoCode with USA returns US`() {
        val result = CountryFlagUtil.getCountryIsoCode("USA")
        
        assertThat(result).isEqualTo("US")
    }
    
    @Test
    fun `getCountryIsoCode with United Kingdom returns GB`() {
        val result = CountryFlagUtil.getCountryIsoCode("United Kingdom")
        
        assertThat(result).isEqualTo("GB")
    }
    
    @Test
    fun `getCountryIsoCode with Mexico returns MX`() {
        val result = CountryFlagUtil.getCountryIsoCode("Mexico")
        
        assertThat(result).isEqualTo("MX")
    }
    
    @Test
    fun `getCountryIsoCode with unknown country returns null`() {
        val result = CountryFlagUtil.getCountryIsoCode("Unknown Country")
        
        assertThat(result).isNull()
    }
    
    @Test
    fun `getCountryFlagUrl with unknown country returns null`() {
        val result = CountryFlagUtil.getCountryFlagUrl("Unknown Country")
        
        assertThat(result).isNull()
    }
}
