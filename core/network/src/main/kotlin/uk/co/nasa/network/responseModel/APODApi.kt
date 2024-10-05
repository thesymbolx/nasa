package uk.co.nasa.network.responseModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APODApi (
    val copyright: String? = null,
    val date: String,
    val explanation: String,
    @SerialName("hdurl")
    val hdUrl: String,
    val mediaType: String? = null,
    val serviceVersion: String? = null,
    val title: String,
    val url: String
)