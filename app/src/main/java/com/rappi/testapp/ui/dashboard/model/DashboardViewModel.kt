package com.rappi.testapp.ui.dashboard.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.rappi.testapp.common.constants.MovieListType
import com.rappi.testapp.core.data.db.MovieDatabase
import com.rappi.testapp.core.data.models.entity.Movie
import com.rappi.testapp.core.data.repository.MoviesRepository
import com.rappi.testapp.core.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class DashboardViewModel @ViewModelInject constructor(
    private val database: MovieDatabase,
    private val repository: MoviesRepository
) : BaseViewModel() {

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)
    private val movieListType = MutableLiveData<MovieListType>(MovieListType.Popular)

    val moviesFlow = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        movieListType.asFlow()
            .flatMapLatest { pagedMoviesFlow(it) }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)

    private fun pagedMoviesFlow(movieListType: MovieListType): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(20), remoteMediator = buildMediator(movieListType)) {
            repository.getMoviesFromLocal()
        }.flow
    }

    fun setPopularMoviesState() = setMovieOrderState(MovieListType.Popular)

    fun setTopRatedMovieState() = setMovieOrderState(MovieListType.TopRated)

    private fun setMovieOrderState(type: MovieListType) {
        if (movieListType.value == type) return

        clearListCh.offer(Unit)
        movieListType.value = type
    }

    private fun buildMediator(type: MovieListType): MoviePageRemoteMediator {
        return MoviePageRemoteMediator(database) { page ->
            repository.fetchMovies(type, page)
        }
    }
}