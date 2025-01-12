package uk.co.nasa.astronomyPictures.dataSource

import uk.co.nasa.database.dao.ApodFavoritesDao
import uk.co.nasa.database.entities.ApodFavorite
import javax.inject.Inject

internal class ApodFavoritesDataSource @Inject constructor(
    private val apodFavoritesDao: ApodFavoritesDao
) {
    fun getFavorites() = apodFavoritesDao.getAll()

    fun saveFavorite(imageUrl: String) =
        apodFavoritesDao.insertAll(
            ApodFavorite(imageUrl = imageUrl),
        )
}