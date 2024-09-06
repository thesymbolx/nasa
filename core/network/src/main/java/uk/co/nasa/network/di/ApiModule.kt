package uk.co.nasa.network.di

import dagger.Provides
import retrofit2.Retrofit
import uk.co.nasa.network.endpoints.AstronomyPictureOfTheDay


interface ApiModule {
    @Provides
    fun providesAPOD(retrofit: Retrofit) =
        retrofit.create(AstronomyPictureOfTheDay::class.java)
}