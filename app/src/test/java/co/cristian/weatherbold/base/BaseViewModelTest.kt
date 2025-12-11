package co.cristian.weatherbold.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

/**
 * Base class for ViewModel tests
 * Handles coroutine test dispatcher setup
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseViewModelTest {
    
    protected val testDispatcher = StandardTestDispatcher()
    
    @Before
    open fun setup() {
        Dispatchers.setMain(testDispatcher)
    }
    
    @After
    open fun tearDown() {
        Dispatchers.resetMain()
    }
}
