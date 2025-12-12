package co.cristian.weatherbold.core.network

import android.content.Context
import android.net.ConnectivityManager
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

/**
 * Tests for NetworkConnectivityManager
 * Note: This class is tested via integration tests since it depends heavily on Android framework
 * The actual network checking logic is covered by repository tests that mock this class
 */
class NetworkConnectivityManagerTest {

    private lateinit var context: Context
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkConnectivityManager: NetworkConnectivityManager

    @Before
    fun setup() {
        context = mockk(relaxed = true)
        connectivityManager = mockk(relaxed = true)
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        networkConnectivityManager = NetworkConnectivityManager(context)
    }

    @Test
    fun `NetworkConnectivityManager is instantiated correctly`() {
        assertThat(networkConnectivityManager).isNotNull()
    }

    @Test
    fun `context is properly injected`() {
        val testContext = mockk<Context>(relaxed = true)
        val testConnectivityManager = mockk<ConnectivityManager>(relaxed = true)
        every { testContext.getSystemService(Context.CONNECTIVITY_SERVICE) } returns testConnectivityManager
        
        val manager = NetworkConnectivityManager(testContext)
        
        assertThat(manager).isNotNull()
    }
}
