package uk.co.nasa.astronomyPictures.repository

import uk.co.nasa.astronomyPictures.dataSource.ApodRemoteDataSource
import uk.co.nasa.astronomyPictures.model.APOD
import uk.co.nasa.astronomyPictures.model.toAPOD
import uk.co.nasa.network.responseModel.APODApi
import uk.co.nasa.network.result.NetworkResult
import uk.co.nasa.network.result.map
import java.time.LocalDate
import javax.inject.Inject

interface AstronomyPicturesRepository {
    suspend fun getPicturesOfTheDay(
        startDate: LocalDate? = null,
        endDate: LocalDate? = null
    ): NetworkResult<List<APOD>>
}

internal class AstronomyPicturesRepositoryImpl @Inject constructor(
    private val apodRemoteDataSource: ApodRemoteDataSource
) : AstronomyPicturesRepository {

    override suspend fun getPicturesOfTheDay(
        startDate: LocalDate?,
        endDate: LocalDate?
    ): NetworkResult<List<APOD>> =
        apodRemoteDataSource.getPictureOfTheDay(
            date = null,
            startDate = startDate.toString(),
            endDate = endDate.toString(),
            count = null,
            thumbs = null
        ).map(List<APODApi>::toAPOD)

}

