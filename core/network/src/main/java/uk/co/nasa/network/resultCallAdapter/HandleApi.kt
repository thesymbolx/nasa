package uk.co.nasa.network.resultCallAdapter

import retrofit2.HttpException
import retrofit2.Response
import uk.co.nasa.network.result.Result

fun <T : Any> handleApi(
    response: Response<T>
): Result<T> {
    return try {
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body)
        } else {
            Result.Error(code = response.code(), message = response.message(), throwable = null)
        }
    } catch (e: HttpException) {
        Result.Error(code = e.code(), message = e.message(), throwable = null)
    } catch (e: Throwable) {
        Result.Error(code = null, message = e.message, throwable = e)
    }
}