package uk.co.nasa.network.endpoints

import retrofit2.http.GET
import retrofit2.http.Query
import uk.co.nasa.network.responseModel.APODApi
import uk.co.nasa.network.result.NetworkResult

interface AstronomyPictureOfTheDay {

    @GET("planetary/apod")
    suspend fun getPicturesOfTheDay(
        @Query("date") date: String?,
        @Query("start_date") startDate: String?,
        @Query("end_date") endDate: String?,
        @Query("count") count: Int?,
        @Query("thumbs") thumbs: Boolean?
    ) : NetworkResult<List<APODApi>>

}