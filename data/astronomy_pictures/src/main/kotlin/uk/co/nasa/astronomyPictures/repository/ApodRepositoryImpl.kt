package uk.co.nasa.astronomyPictures.repository

import uk.co.nasa.astronomyPictures.dataSource.ApodRemoteDataSource
import uk.co.nasa.astronomyPictures.model.APOD
import uk.co.nasa.astronomyPictures.model.toAPOD
import uk.co.nasa.network.responseModel.APODApi
import uk.co.nasa.network.result.NetworkResult
import uk.co.nasa.network.result.map
import java.time.LocalDate
import javax.inject.Inject

internal class ApodRepositoryImpl @Inject constructor(
    private val apodRemoteDataSource: ApodRemoteDataSource,
    private val apodFavoritesRepository: ApodFavoritesRepository
) : ApodRepository {

    override suspend fun getPicturesOfTheDay(
        startDate: LocalDate?,
        endDate: LocalDate?
    ): NetworkResult<List<APOD>> {
        val favorites = apodFavoritesRepository.getFavorites()

        val apodResult = apodRemoteDataSource.getPictureOfTheDay(
            date = null,
            startDate = startDate.toString().takeIf { startDate != null },
            endDate = endDate.toString().takeIf { endDate != null },
            count = null,
            thumbs = null
        )

        return apodResult.map { apodsApi: List<APODApi> ->
            apodsApi.toAPOD(favorites)
        }
    }

}

