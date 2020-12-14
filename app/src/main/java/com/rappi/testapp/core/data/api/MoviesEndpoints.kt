package com.rappi.testapp.core.data.api

object MoviesEndpoints {

    private const val API_VERSION = "/3"

    const val POPULAR_MOVIES = "$API_VERSION/movie/popular"

    const val TOP_RATED_MOVIES = "$API_VERSION/movie/top_rated"

    const val SEARCH_MOVIES = "$API_VERSION/search/movie"
}
