package co.cristian.weatherbold.core.network

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NetworkResultTest {
    
    @Test
    fun `success result contains data`() {
        val data = "test data"
        val result = NetworkResult.Success(data)
        
        assertThat(result.data).isEqualTo(data)
    }
    
    @Test
    fun `error result contains message and code`() {
        val message = "Error occurred"
        val result: NetworkResult.Error = NetworkResult.Error(message, 404)
        
        assertThat(result.message).isEqualTo(message)
        assertThat(result.code).isEqualTo(404)
    }
    
    @Test
    fun `error result with null code`() {
        val message = "Error occurred"
        val result: NetworkResult.Error = NetworkResult.Error(message, null)
        
        assertThat(result.message).isEqualTo(message)
        assertThat(result.code).isNull()
    }
    
    @Test
    fun `loading result is singleton`() {
        val loading1 = NetworkResult.Loading
        val loading2 = NetworkResult.Loading
        
        assertThat(loading1).isSameInstanceAs(loading2)
    }
}
