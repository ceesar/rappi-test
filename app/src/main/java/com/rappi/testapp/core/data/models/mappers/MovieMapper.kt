package com.rappi.testapp.core.data.models.mappers

import com.rappi.testapp.core.data.models.api.response.MovieResponse
import com.rappi.testapp.core.data.models.entity.Movie

fun MovieResponse.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        voteAverage = voteAverage,
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview,
        releaseDate = releaseDate
    )
}