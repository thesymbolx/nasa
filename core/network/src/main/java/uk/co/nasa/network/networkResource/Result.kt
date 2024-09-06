package uk.co.nasa.network.networkResource

sealed class Result<T : Any> {
    class Success<T: Any>(val data: T) : Result<T>()
    class Error<T: Any>(val code: Int, val message: String?) : Result<T>()
}