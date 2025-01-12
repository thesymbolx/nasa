package uk.co.nasa.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.co.nasa.database.NasaDatabase
import uk.co.nasa.database.dao.ApodFavoritesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): NasaDatabase =
        Room.databaseBuilder(
            context,
            NasaDatabase::class.java, "database-nasa"
        ).build()

    @Provides
    @Singleton
    fun provideApodFavoritesDao(roomDatabase: NasaDatabase): ApodFavoritesDao =
        roomDatabase.apodFavoritesDao()
}