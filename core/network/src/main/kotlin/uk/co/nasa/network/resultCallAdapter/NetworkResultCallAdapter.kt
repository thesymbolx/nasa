package uk.co.nasa.network.resultCallAdapter

import retrofit2.Call
import retrofit2.CallAdapter
import uk.co.nasa.network.result.NetworkResult
import java.lang.reflect.Type


class NetworkResultCallAdapter(
    private val resultType: Type
) : CallAdapter<Type, Call<NetworkResult<Type>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<NetworkResult<Type>> {
        return NetworkResultCall(call)
    }
}