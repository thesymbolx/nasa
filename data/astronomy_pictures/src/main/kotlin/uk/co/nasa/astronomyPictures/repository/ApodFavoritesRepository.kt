package uk.co.nasa.astronomyPictures.repository

import uk.co.nasa.database.entities.ApodFavorite

interface ApodFavoritesRepository {
    fun getFavorites(): List<ApodFavorite>
    fun saveFavorites(imageUrl: String)
}