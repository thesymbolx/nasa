package uk.co.nasa.network.networkResource

fun <T : Any> Result<T>.onSuccess(
    onResult: (T) -> Unit
) = apply {
    if(this is Result.Success) onResult(data)
}

fun <T : Any> Result<T>.onError(
    onResult: (code: Int, message: String?) -> Unit
) = apply {
    if(this is Result.Error) onResult(code, message)
}
