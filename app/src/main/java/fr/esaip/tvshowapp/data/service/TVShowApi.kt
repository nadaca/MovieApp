package fr.esaip.tvshowapp.data.service

import fr.esaip.tvshowapp.data.model.TVDetails
import fr.esaip.tvshowapp.data.model.TVShow
import retrofit2.http.GET
import retrofit2.http.Query

interface TVShowApi {

    @GET("most-popular")
    suspend fun getTVShows(
       @Query("page") page : Int = 1
    ) : TVShow

    @GET("show-details")
    suspend fun getTVShowDetails(
       @Query("q") id : Int
    ) : TVDetails

    @GET("search")
    suspend fun searchTVShows(
       @Query("q") query : String,
       @Query("page") page : Int = 1
    ) : TVShow

}