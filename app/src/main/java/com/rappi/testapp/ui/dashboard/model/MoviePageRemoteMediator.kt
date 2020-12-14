package com.rappi.testapp.ui.dashboard.model

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rappi.testapp.core.data.db.MovieDatabase
import com.rappi.testapp.core.data.models.api.response.MoviesResponse
import com.rappi.testapp.core.data.models.entity.Movie
import com.rappi.testapp.core.data.models.entity.RemoteKey
import com.rappi.testapp.core.data.models.mappers.toDomain
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class MoviePageRemoteMediator constructor(
    private val database: MovieDatabase,
    private val fetchData: suspend (page: Int?) -> MoviesResponse
) : RemoteMediator<Int, Movie>() {

    private val movieDao = database.movieDao()
    private val remoteKeyDao = database.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKeyByLabel("movie")
                    }

                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextKey
                }
            }

            val moviesResponse = fetchData(page)

            //check if the next page is valid
            val nextPage = if (moviesResponse.page + 1 <= moviesResponse.totalPages) {
                moviesResponse.page + 1
            } else {
                null
            }

            if (loadType == LoadType.REFRESH) {
                database.withTransaction {
                    movieDao.deleteMovies()
                    remoteKeyDao.deleteByQuery("movie")
                }
            }

            database.withTransaction {
                remoteKeyDao.insertOrReplace(RemoteKey("movie", nextPage))
                movieDao.insertAll(moviesResponse.results.map { it.toDomain() })
            }

            return MediatorResult.Success(endOfPaginationReached = nextPage == null)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}