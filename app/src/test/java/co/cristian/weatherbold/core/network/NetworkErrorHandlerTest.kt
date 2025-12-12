package co.cristian.weatherbold.core.network

import com.google.common.truth.Truth.assertThat
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrorHandlerTest {

    private lateinit var errorHandler: NetworkErrorHandler

    @Before
    fun setup() {
        errorHandler = NetworkErrorHandler()
    }

    @Test
    fun `handleError with HttpException 400 returns bad request error`() {
        val exception = HttpException(Response.error<Any>(400, "".toResponseBody()))

        val result = errorHandler.handleError(exception)

        assertThat(result).isInstanceOf(NetworkResult.Error::class.java)
        assertThat(result.message).contains("inválida")
        assertThat(result.code).isEqualTo(400)
    }

    @Test
    fun `handleError with HttpException 401 returns unauthorized error`() {
        val exception = HttpException(Response.error<Any>(401, "".toResponseBody()))

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("autorizado")
        assertThat(result.code).isEqualTo(401)
    }

    @Test
    fun `handleError with HttpException 403 returns forbidden error`() {
        val exception = HttpException(Response.error<Any>(403, "".toResponseBody()))

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("prohibido")
        assertThat(result.code).isEqualTo(403)
    }

    @Test
    fun `handleError with HttpException 404 returns not found error`() {
        val exception = HttpException(Response.error<Any>(404, "".toResponseBody()))

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("no encontrado")
        assertThat(result.code).isEqualTo(404)
    }

    @Test
    fun `handleError with HttpException 408 returns timeout error`() {
        val exception = HttpException(Response.error<Any>(408, "".toResponseBody()))

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("Tiempo de espera")
        assertThat(result.code).isEqualTo(408)
    }

    @Test
    fun `handleError with HttpException 429 returns rate limit error`() {
        val exception = HttpException(Response.error<Any>(429, "".toResponseBody()))

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("Demasiadas solicitudes")
        assertThat(result.code).isEqualTo(429)
    }

    @Test
    fun `handleError with HttpException 500 returns server error`() {
        val exception = HttpException(Response.error<Any>(500, "".toResponseBody()))

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("servidor")
        assertThat(result.code).isEqualTo(500)
    }

    @Test
    fun `handleError with HttpException 502 returns bad gateway error`() {
        val exception = HttpException(Response.error<Any>(502, "".toResponseBody()))

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("no disponible")
        assertThat(result.code).isEqualTo(502)
    }

    @Test
    fun `handleError with HttpException 503 returns service unavailable`() {
        val exception = HttpException(Response.error<Any>(503, "".toResponseBody()))

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("no disponible")
        assertThat(result.code).isEqualTo(503)
    }

    @Test
    fun `handleError with unmapped HTTP code returns generic server error`() {
        val exception = HttpException(Response.error<Any>(418, "".toResponseBody()))

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("Error del servidor")
        assertThat(result.message).contains("418")
        assertThat(result.code).isEqualTo(418)
    }

    @Test
    fun `handleError with SocketTimeoutException returns timeout error`() {
        val exception = SocketTimeoutException("Timeout")

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("Tiempo de espera")
        assertThat(result.code).isNull()
    }

    @Test
    fun `handleError with UnknownHostException returns connection error`() {
        val exception = UnknownHostException("Unknown host")

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("conectar al servidor")
        assertThat(result.code).isNull()
    }

    @Test
    fun `handleError with IOException returns connection error`() {
        val exception = IOException("IO Error")

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("conexión")
        assertThat(result.code).isNull()
    }

    @Test
    fun `handleError with generic exception returns unknown error`() {
        val exception = RuntimeException("Unknown error")

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("Unknown error")
        assertThat(result.code).isNull()
    }

    @Test
    fun `handleError with exception without message returns default message`() {
        val exception = RuntimeException()

        val result = errorHandler.handleError(exception)

        assertThat(result.message).contains("Error desconocido")
        assertThat(result.code).isNull()
    }

    @Test
    fun `handleError preserves exception in result`() {
        val exception = IOException("Test error")

        val result = errorHandler.handleError(exception)

        assertThat(result.exception).isEqualTo(exception)
    }
}
