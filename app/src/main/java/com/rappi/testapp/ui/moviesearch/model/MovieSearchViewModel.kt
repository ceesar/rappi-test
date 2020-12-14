package com.rappi.testapp.ui.moviesearch.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.rappi.testapp.core.data.models.entity.Movie
import com.rappi.testapp.core.data.repository.MoviesRepository
import com.rappi.testapp.core.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@OptIn(ExperimentalPagingApi::class)
@ExperimentalCoroutinesApi
class MovieSearchViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository
) : BaseViewModel() {

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)
    private val queryData = MutableLiveData("")

    val moviesFlow = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        queryData.asFlow()
            .flatMapLatest { pagedMoviesFlow(it) }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)

    private fun pagedMoviesFlow(query: String): Flow<PagingData<Movie>> {
        return Pager(config = PagingConfig(20)) {
            SearchablePagingSource(repository, query)
        }.flow
    }

    fun performSearch(query: String) {
        if (queryData.value == query) return

        clearListCh.offer(Unit)
        queryData.value = query
    }
}