package uk.co.nasa.astronomyPictures.model

import uk.co.nasa.database.entities.ApodFavorite
import uk.co.nasa.network.responseModel.APODApi
import uk.co.nasa.network.responseModel.MediaType

data class APOD (
    val description: String,
    val title: String,
    val url: String?,
    val favorite: Boolean,
    val mediaType: MediaType
)

fun APODApi.toAPOD(favorite: Boolean) = APOD(
    description = explanation,
    title = title,
    url = url,
    favorite = favorite,
    mediaType = mediaType
)

fun List<APODApi>.toAPOD(imageUrls: List<ApodFavorite>): List<APOD> = map { apod ->
    apod.toAPOD(imageUrls.any { it.imageUrl == apod.url })
}