package uk.co.nasa.astronomyPictures.repository

import uk.co.nasa.astronomyPictures.dataSource.ApodFavoritesDataSource
import javax.inject.Inject

internal class ApodFavoritesRepositoryImpl @Inject constructor(
    private val apodFavoritesDataSource: ApodFavoritesDataSource
) : ApodFavoritesRepository {
    override fun getFavorites() = apodFavoritesDataSource.getFavorites()

    override fun saveFavorites(imageUrl: String) =
        apodFavoritesDataSource.saveFavorite(imageUrl)
}