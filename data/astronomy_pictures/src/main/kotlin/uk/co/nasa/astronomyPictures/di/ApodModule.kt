package uk.co.nasa.astronomyPictures.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import uk.co.nasa.astronomyPictures.repository.ApodFavoritesRepository
import uk.co.nasa.astronomyPictures.repository.ApodFavoritesRepositoryImpl
import uk.co.nasa.astronomyPictures.repository.ApodRepository
import uk.co.nasa.astronomyPictures.repository.ApodRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface MyModule {
    @Binds
    abstract fun bindApodRepository(impl: ApodRepositoryImpl): ApodRepository

    @Binds
    abstract fun bindApodFavoritesRepository(impl: ApodFavoritesRepositoryImpl): ApodFavoritesRepository
}