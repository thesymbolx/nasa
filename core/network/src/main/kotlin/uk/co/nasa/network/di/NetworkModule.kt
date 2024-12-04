package uk.co.nasa.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import uk.co.nasa.network.API_KEY
import uk.co.nasa.network.BASE_URL
import uk.co.nasa.network.resultCallAdapter.NetworkResultCallAdapterFactory
import javax.inject.Named
import javax.inject.Singleton

private const val API_KEY_INTERCEPTOR = "apiKeyInterceptor"

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Named(API_KEY_INTERCEPTOR)
    fun provideApiKeyInterceptor()  =
        Interceptor { chain: Interceptor. Chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val requestBuilder = original.newBuilder()
                .url(url)

            val request =  requestBuilder.build()
            chain.proceed(request)
        }

    @Provides
    @Singleton
    fun okHttpCallFactory(
        @Named(API_KEY_INTERCEPTOR) apiKeyInterceptor: Interceptor
    ): Call.Factory =
        OkHttpClient
            .Builder()
            .addNetworkInterceptor(apiKeyInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        json: Json,
        okhttpCallFactory: dagger.Lazy<Call.Factory>
    ) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // lazy injection to prevent initializing OkHttp on the main thread. See network layer readme.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .build()
}