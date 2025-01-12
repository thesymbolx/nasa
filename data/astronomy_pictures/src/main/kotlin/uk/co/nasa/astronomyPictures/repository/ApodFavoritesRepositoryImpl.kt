package uk.co.nasa.astronomyPictures.repository

import uk.co.nasa.astronomyPictures.dataSource.ApodFavoritesDataSource
import javax.inject.Inject

internal class ApodFavoritesRepositoryImpl @Inject constructor(
    private val apodFavoritesDataSource: ApodFavoritesDataSource
) : ApodFavoritesRepository {
    override suspend fun getFavorites() = apodFavoritesDataSource.getFavorites()

    override suspend fun saveFavorites(imageUrl: String) =
        apodFavoritesDataSource.saveFavorite(imageUrl)

    override suspend fun removeFavorite(imageUrl: String) {
        apodFavoritesDataSource.removeFavorite(imageUrl)
    }
}