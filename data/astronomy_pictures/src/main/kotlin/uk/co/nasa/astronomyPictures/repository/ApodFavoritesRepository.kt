package uk.co.nasa.astronomyPictures.repository

import uk.co.nasa.database.entities.ApodFavorite

interface ApodFavoritesRepository {
    suspend fun getFavorites(): List<ApodFavorite>
    suspend fun saveFavorites(imageUrl: String)
    suspend fun removeFavorite(imageUrl: String)
}