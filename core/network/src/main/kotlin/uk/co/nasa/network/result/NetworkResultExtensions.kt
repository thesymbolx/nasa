package uk.co.nasa.network.result

fun <T : Any> NetworkResult<T>.onSuccess(
    onResult: (T) -> Unit
) = apply {
    if(this is NetworkResult.Success) onResult(data)
}

fun <T : Any> NetworkResult<T>.onError(
    onResult: (code: Int?, message: String?, throwable: Throwable?) -> Unit
) = apply {
    if(this is NetworkResult.Error) onResult(code, message, throwable)
}

fun <T : Any, R : Any> NetworkResult<T>.map(
    mapper: (T) -> R
) : NetworkResult<R> {
    return when(this) {
        is NetworkResult.Success -> NetworkResult.Success(mapper(data))
        is NetworkResult.Error -> NetworkResult.Error(code, message, throwable)
    }
}
