package uk.co.nasa.network.resultCallAdapter

import retrofit2.HttpException
import retrofit2.Response
import uk.co.nasa.network.result.NetworkResult

fun <T : Any> handleApi(
    response: Response<T>
): NetworkResult<T> {
    return try {
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error(code = response.code(), message = response.message(), throwable = null)
        }
    } catch (e: HttpException) {
        NetworkResult.Error(code = e.code(), message = e.message(), throwable = e)
    } catch (e: Throwable) {
        NetworkResult.Error(code = null, message = e.message, throwable = e)
    }
}