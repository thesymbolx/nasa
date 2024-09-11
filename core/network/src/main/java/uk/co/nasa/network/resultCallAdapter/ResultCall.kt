package uk.co.nasa.network.resultCallAdapter

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.nasa.network.result.Result


class ResultCall<T : Any>(
    private val proxy: Call<T>
) : Call<Result<T>> {
    override fun execute(): Response<Result<T>> = throw NotImplementedError()
    override fun clone(): Call<Result<T>> = ResultCall(proxy.clone())
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() { proxy.cancel() }

    override fun enqueue(callback: Callback<Result<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResult = handleApi (response)
                callback.onResponse(this@ResultCall, Response.success(networkResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult = Result.Error<T>(
                    code = null,
                    message = t.message,
                    throwable = t
                )
                callback.onResponse(this@ResultCall, Response.success(networkResult))
            }
        })
    }
}