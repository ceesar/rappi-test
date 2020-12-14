package com.rappi.testapp.ui.dashboard.view

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.rappi.testapp.R
import com.rappi.testapp.core.data.models.entity.Movie
import com.rappi.testapp.databinding.FragmentDashboardBinding
import com.rappi.testapp.ui.dashboard.adapter.MovieLoadStateAdapter
import com.rappi.testapp.ui.dashboard.adapter.MoviePagerAdapter
import com.rappi.testapp.ui.dashboard.model.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var viewBinding: FragmentDashboardBinding
    private lateinit var adapter: MoviePagerAdapter
    private val viewModel: DashboardViewModel by viewModels { defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDashboardBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        initBottomNavigationMenu()
        initMovieAdapter()
        initSwipeToRefresh()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        val item = menu.findItem(R.id.search_action)
        item.setOnMenuItemClickListener {
            moveToSearchableFragment()
            true
        }
    }

    private fun initBottomNavigationMenu() {
        viewBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.iPopular -> {
                    viewModel.setPopularMoviesState()
                    true
                }
                R.id.iTopRated -> {
                    viewModel.setTopRatedMovieState()
                    true
                }
                else -> false
            }
        }
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

        val action = DashboardFragmentDirections.actionDashboardToMovieDetail(movie)
        findNavController().navigate(action, extras)
    }

    private fun moveToSearchableFragment() {
        val action = DashboardFragmentDirections.actionDashboardToSearchMovie()
        findNavController().navigate(action)
    }
}