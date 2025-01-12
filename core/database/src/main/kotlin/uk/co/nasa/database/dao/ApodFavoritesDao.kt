package uk.co.nasa.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.co.nasa.database.entities.ApodFavorite

@Dao
interface ApodFavoritesDao {
    @Query("SELECT * FROM apodfavorite")
    suspend fun getAll(): List<ApodFavorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: ApodFavorite)

    @Delete
    suspend fun delete(user: ApodFavorite)
}