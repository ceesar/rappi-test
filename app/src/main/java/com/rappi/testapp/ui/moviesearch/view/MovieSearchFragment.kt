package com.rappi.testapp.ui.moviesearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.rappi.testapp.core.data.models.entity.Movie
import com.rappi.testapp.databinding.FragmentMovieSearchBinding
import com.rappi.testapp.ui.dashboard.adapter.MovieLoadStateAdapter
import com.rappi.testapp.ui.dashboard.adapter.MoviePagerAdapter
import com.rappi.testapp.ui.moviesearch.model.MovieSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@ExperimentalCoroutinesApi
@OptIn(FlowPreview::class)
@AndroidEntryPoint
class MovieSearchFragment : Fragment() {

    private lateinit var viewBinding: FragmentMovieSearchBinding
    private lateinit var adapter: MoviePagerAdapter
    private val viewModel: MovieSearchViewModel by viewModels { defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMovieSearchBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMovieAdapter()
        initSearchableComponent()
        initSwipeToRefresh()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSearchableComponent() {
        viewBinding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.performSearch(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun initMovieAdapter() {
        adapter = MoviePagerAdapter { movie, imageView ->
            movieSelectedAction(movie, imageView)
        }
        viewBinding.moviesRecycler.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter(adapter),
            footer = MovieLoadStateAdapter(adapter)
        )

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                viewBinding.swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.moviesFlow.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { viewBinding.moviesRecycler.scrollToPosition(0) }
        }
    }

    private fun initSwipeToRefresh() {
        viewBinding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    private fun movieSelectedAction(movie: Movie, imageView: ImageView) {
        imageView.transitionName = movie.title
        val extras = FragmentNavigatorExtras(
            imageView to movie.title
        )

        val action = MovieSearchFragmentDirections.actionSearchMovieToMovieDetail(movie)
        findNavController().navigate(action, extras)
    }
}