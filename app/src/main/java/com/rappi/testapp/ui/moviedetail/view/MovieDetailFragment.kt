package com.rappi.testapp.ui.moviedetail.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.rappi.testapp.R
import com.rappi.testapp.common.extensions.loadFromUrl
import com.rappi.testapp.core.view.BaseFragment
import com.rappi.testapp.databinding.FragmentMovieDetailBinding
import com.rappi.testapp.ui.moviedetail.model.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {

    private val args: MovieDetailFragmentArgs by navArgs()
    private val viewModel: MovieDetailViewModel by viewModels {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.movieDetail = args.movieDetail
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.ivMoviePoster.loadFromUrl(viewModel.movieDetail.getFullBackdropPath())
        viewDataBinding.ivMovieThumbnail.apply {
            transitionName = viewModel.movieDetail.title
            loadFromUrl(viewModel.movieDetail.getFullPosterPath())
        }
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

    override fun getLayoutRes() = R.layout.fragment_movie_detail

    override fun getInternalViewModel() = viewModel
}