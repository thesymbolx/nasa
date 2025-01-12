package uk.co.nasa.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import uk.co.nasa.database.entities.ApodFavorite

@Dao
interface ApodFavoritesDao {
    @Query("SELECT * FROM apodfavorite")
    fun getAll(): List<ApodFavorite>

    @Query("SELECT * FROM apodfavorite WHERE uid IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<ApodFavorite>

    @Insert
    fun insertAll(vararg users: ApodFavorite)

    @Delete
    fun delete(user: ApodFavorite)
}