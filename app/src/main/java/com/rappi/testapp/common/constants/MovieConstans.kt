package com.rappi.testapp.common.constants

sealed class MovieListType {
    object Popular : MovieListType()
    object TopRated : MovieListType()
}