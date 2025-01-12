package uk.co.nasa.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.co.nasa.database.dao.ApodFavoritesDao
import uk.co.nasa.database.entities.ApodFavorite


@Database(entities = [ApodFavorite::class], version = 1)
abstract class NasaDatabase : RoomDatabase() {
    abstract fun apodFavoritesDao(): ApodFavoritesDao
}