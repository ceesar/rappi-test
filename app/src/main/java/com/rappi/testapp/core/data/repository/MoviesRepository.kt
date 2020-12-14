package com.rappi.testapp.core.data.repository

import com.rappi.testapp.common.constants.MovieListType
import com.rappi.testapp.core.data.api.MovieApi
import com.rappi.testapp.core.data.api.MoviesEndpoints
import com.rappi.testapp.core.data.db.MovieDao
import com.rappi.testapp.core.data.models.api.response.MoviesResponse
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val movieServices: MovieApi,
    private val movieDao: MovieDao
) : BaseRepository() {

    suspend fun fetchMovies(movieListType: MovieListType, page: Int?): MoviesResponse {
        return when (movieListType) {
            is MovieListType.Popular -> getPopularMovies(page)
            is MovieListType.TopRated -> getTopRatedMovies(page)
        }
    }

    private suspend fun getPopularMovies(page: Int?): MoviesResponse {
        return movieServices.getMovies(MoviesEndpoints.POPULAR_MOVIES, page)
    }

    private suspend fun getTopRatedMovies(page: Int?): MoviesResponse {
        return movieServices.getMovies(MoviesEndpoints.TOP_RATED_MOVIES, page)
    }

    suspend fun searchMovies(page: Int?, query: String): MoviesResponse {
        return movieServices.searchMovie(page, query)
    }

    fun getMoviesFromLocal() = movieDao.getMovies()
}