package co.cristian.weatherbold.core.network

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NetworkResultExtensionsTest {
    
    @Test
    fun `NetworkResult Success contains data`() {
        val result = NetworkResult.Success("test data")
        
        assertThat(result.data).isEqualTo("test data")
    }
    
    @Test
    fun `NetworkResult Error contains message`() {
        val result = NetworkResult.Error("Error message", 404, null)
        
        assertThat(result.message).isEqualTo("Error message")
        assertThat(result.code).isEqualTo(404)
    }
    
    @Test
    fun `NetworkResult Error can have null code`() {
        val result = NetworkResult.Error("Error message", null, null)
        
        assertThat(result.message).isEqualTo("Error message")
        assertThat(result.code).isNull()
    }
    
    @Test
    fun `NetworkResult Loading is singleton`() {
        val loading1 = NetworkResult.Loading
        val loading2 = NetworkResult.Loading
        
        assertThat(loading1).isSameInstanceAs(loading2)
    }
    
    @Test
    fun `NetworkResult Success with different types`() {
        val stringResult = NetworkResult.Success("text")
        val intResult = NetworkResult.Success(42)
        val listResult = NetworkResult.Success(listOf(1, 2, 3))
        
        assertThat(stringResult.data).isEqualTo("text")
        assertThat(intResult.data).isEqualTo(42)
        assertThat(listResult.data).hasSize(3)
    }
    
    @Test
    fun `NetworkResult Error with different codes`() {
        val notFound = NetworkResult.Error("Not found", 404, null)
        val unauthorized = NetworkResult.Error("Unauthorized", 401, null)
        val serverError = NetworkResult.Error("Server error", 500, null)
        
        assertThat(notFound.code).isEqualTo(404)
        assertThat(unauthorized.code).isEqualTo(401)
        assertThat(serverError.code).isEqualTo(500)
    }
    
    @Test
    fun `NetworkResult Error with exception`() {
        val exception = RuntimeException("Test exception")
        val result = NetworkResult.Error("Error with exception", 500, exception)
        
        assertThat(result.exception).isEqualTo(exception)
        assertThat(result.exception?.message).isEqualTo("Test exception")
    }
}
