package uk.co.nasa.astronomyPictures.dataSource

import uk.co.nasa.network.endpoints.AstronomyPictureOfTheDay
import uk.co.nasa.network.responseModel.APODApi
import uk.co.nasa.network.result.NetworkResult
import javax.inject.Inject

internal class ApodRemoteDataSource @Inject constructor(
    private val astronomyPictureOfTheDay: AstronomyPictureOfTheDay
) {
    suspend fun getPictureOfTheDay(
        date: String?,
        startDate: String?,
        endDate: String?,
        count: Int?,
        thumbs: Boolean?
    ) : NetworkResult<List<APODApi>> =
        astronomyPictureOfTheDay.getPicturesOfTheDay(date, startDate, endDate, count, thumbs)
}