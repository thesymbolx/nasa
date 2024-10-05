package uk.co.nasa.astronomyPictures.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uk.co.nasa.astronomyPictures.repository.AstronomyPicturesRepository
import uk.co.nasa.astronomyPictures.repository.AstronomyPicturesRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
internal interface MyModule {
    @Binds
    abstract fun bindMyDependency(impl: AstronomyPicturesRepositoryImpl): AstronomyPicturesRepository
}