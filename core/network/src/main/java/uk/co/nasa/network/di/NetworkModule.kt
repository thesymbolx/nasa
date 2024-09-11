package uk.co.nasa.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory =
        OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(
        json: Json,
        okhttpCallFactory: dagger.Lazy<Call.Factory>
    ) =
        Retrofit.Builder()
            .baseUrl("https://example.com/")
            // lazy injection to prevent initializing OkHttp on the main thread. See network layer readme.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .build()
}