package uk.co.nasa.network.endpoints

import retrofit2.http.GET
import retrofit2.http.Query
import uk.co.nasa.network.responseModel.APOD

interface AstronomyPictureOfTheDay {

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query("date") date: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("count") count: Int,
        @Query("thumbs") thumbs: Boolean
    ) : APOD

}