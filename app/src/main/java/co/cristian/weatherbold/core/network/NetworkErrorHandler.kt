package co.cristian.weatherbold.core.network

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkErrorHandler @Inject constructor() {

    fun handleError(exception: Throwable): NetworkResult.Error {
        return when (exception) {
            is HttpException -> handleHttpException(exception)
            is SocketTimeoutException -> NetworkResult.Error(
                message = "Tiempo de espera agotado. Por favor, intenta de nuevo.",
                code = null,
                exception = exception
            )
            is UnknownHostException -> NetworkResult.Error(
                message = "No se pudo conectar al servidor. Verifica tu conexión a internet.",
                code = null,
                exception = exception
            )
            is IOException -> NetworkResult.Error(
                message = "Error de conexión. Por favor, verifica tu internet.",
                code = null,
                exception = exception
            )
            else -> NetworkResult.Error(
                message = exception.message ?: "Error desconocido. Intenta de nuevo.",
                code = null,
                exception = exception
            )
        }
    }
    
    /**
     * HTTP Errors
     */
    private fun handleHttpException(exception: HttpException): NetworkResult.Error {
        val code = exception.code()
        val message = when (code) {
            400 -> "Solicitud inválida. Verifica los datos ingresados."
            401 -> "No autorizado. Verifica tu API Key."
            403 -> "Acceso prohibido."
            404 -> "Recurso no encontrado."
            408 -> "Tiempo de espera agotado."
            429 -> "Demasiadas solicitudes. Intenta más tarde."
            500 -> "Error del servidor. Intenta más tarde."
            502 -> "Servidor no disponible."
            503 -> "Servicio no disponible temporalmente."
            else -> "Error del servidor (código: $code)"
        }
        
        return NetworkResult.Error(
            message = message,
            code = code,
            exception = exception
        )
    }
}
