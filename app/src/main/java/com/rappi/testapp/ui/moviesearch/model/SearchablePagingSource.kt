package com.rappi.testapp.ui.moviesearch.model

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.rappi.testapp.core.data.models.entity.Movie
import com.rappi.testapp.core.data.models.mappers.toDomain
import com.rappi.testapp.core.data.repository.MoviesRepository
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class SearchablePagingSource constructor(
    private val repository: MoviesRepository,
    private val query: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val moviesResponse = repository.searchMovies(
                page = if (params is LoadParams.Append) params.key else null,
                query = query
            )

            val nextPage = if (moviesResponse.page + 1 <= moviesResponse.totalPages) {
                moviesResponse.page + 1
            } else {
                null
            }

            LoadResult.Page(
                data = moviesResponse.results.map { it.toDomain() },
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}