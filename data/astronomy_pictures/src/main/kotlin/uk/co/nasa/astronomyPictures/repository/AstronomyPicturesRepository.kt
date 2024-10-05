package uk.co.nasa.astronomyPictures.repository

import uk.co.nasa.astronomyPictures.dataSource.ApodRemoteDataSource
import uk.co.nasa.astronomyPictures.model.APOD
import uk.co.nasa.astronomyPictures.model.toAPOD
import uk.co.nasa.network.responseModel.APODApi
import uk.co.nasa.network.result.NetworkResult
import uk.co.nasa.network.result.map
import javax.inject.Inject

interface AstronomyPicturesRepository {
    suspend fun getPictureOfTheDay(): NetworkResult<APOD>
}

internal class AstronomyPicturesRepositoryImpl @Inject constructor(
    private val apodRemoteDataSource: ApodRemoteDataSource
) : AstronomyPicturesRepository {
    override suspend fun getPictureOfTheDay(): NetworkResult<APOD> =
        apodRemoteDataSource.getPictureOfTheDay(
            date = null,
            startDate = null,
            endDate = null,
            count = null,
            thumbs = null
        ).map(APODApi::toAPOD)
}

