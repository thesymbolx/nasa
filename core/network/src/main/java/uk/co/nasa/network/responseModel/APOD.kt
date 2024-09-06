package uk.co.nasa.network.responseModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APOD (
    val copyright: String,
    val date: String,
    val explanation: String,
    @SerialName("hdurl")
    val hdUrl: String,
    val mediaType: String,
    val serviceVersion: String,
    val title: String,
    val url: String
)