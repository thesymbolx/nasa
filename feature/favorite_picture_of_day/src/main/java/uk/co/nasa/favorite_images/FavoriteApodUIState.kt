package uk.co.nasa.favorite_images

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FavoriteApodUIState (
    val imageUrls: ImmutableList<String> = persistentListOf()
)