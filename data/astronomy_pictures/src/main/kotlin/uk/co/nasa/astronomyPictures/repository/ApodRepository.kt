package uk.co.nasa.astronomyPictures.repository

import uk.co.nasa.astronomyPictures.model.APOD
import uk.co.nasa.network.result.NetworkResult
import java.time.LocalDate

interface ApodRepository {
    suspend fun getPicturesOfTheDay(
        startDate: LocalDate? = null,
        endDate: LocalDate? = null
    ): NetworkResult<List<APOD>>
}