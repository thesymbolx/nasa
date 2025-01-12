package uk.co.nasa.astronomyPictures.dataSource

import uk.co.nasa.database.dao.ApodFavoritesDao
import uk.co.nasa.database.entities.ApodFavorite
import javax.inject.Inject

internal class ApodFavoritesDataSource @Inject constructor(
    private val apodFavoritesDao: ApodFavoritesDao
) {
    suspend fun getFavorites() = apodFavoritesDao.getAll()

    suspend fun saveFavorite(imageUrl: String) =
        apodFavoritesDao.insert(ApodFavorite(imageUrl = imageUrl))

    suspend fun removeFavorite(imageUrl: String) =
        apodFavoritesDao.delete(ApodFavorite(imageUrl = imageUrl))
}