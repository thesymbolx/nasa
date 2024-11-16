package uk.co.nasa.astronomyPictures.model

import uk.co.nasa.network.responseModel.APODApi

data class APOD (
    val description: String,
    val title: String,
    val url: String
)

fun APODApi.toAPOD() = APOD(
    description = explanation,
    title = title,
    url = url
)


fun List<APODApi>.toAPOD(): List<APOD> = map {
    it.toAPOD()
}