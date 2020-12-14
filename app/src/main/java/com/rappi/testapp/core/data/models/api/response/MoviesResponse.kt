package com.rappi.testapp.core.data.models.api.response

import com.squareup.moshi.Json

data class MoviesResponse(
    val page: Int,
    @field:Json(name = "total_pages")
    val totalPages: Int,
    val results: List<MovieResponse>
)

data class MovieResponse(
    val id: Int,
    val title: String,
    @field:Json(name = "vote_average")
    val voteAverage: Float,
    @field:Json(name = "backdrop_path")
    val backdropPath: String,
    @field:Json(name = "poster_path")
    val posterPath: String,
    val overview: String,
    @field:Json(name = "release_date")
    val releaseDate: String
)