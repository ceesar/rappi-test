package com.rappi.testapp.core.data.api

import com.rappi.testapp.core.data.models.api.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MovieApi {

    @GET
    suspend fun getMovies(@Url endpoint: String, @Query("page") page: Int?): MoviesResponse

    @GET(MoviesEndpoints.SEARCH_MOVIES)
    suspend fun searchMovie(
        @Query("page") page: Int?, @Query("query") query: String
    ): MoviesResponse
}