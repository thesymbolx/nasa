package uk.co.nasa.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uk.co.nasa.network.endpoints.AstronomyPictureOfTheDay

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {
    @Provides
    fun providesAPOD(retrofit: Retrofit) =
        retrofit.create(AstronomyPictureOfTheDay::class.java)
}