package com.rappi.testapp.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rappi.testapp.R
import com.rappi.testapp.common.extensions.clearImageLoad
import com.rappi.testapp.common.extensions.loadFromUrl
import com.rappi.testapp.core.data.models.entity.Movie
import com.rappi.testapp.databinding.ItemMovieBinding

class MoviePagerAdapter(
    private val itemSelectedAction: (movie: Movie, imageView: ImageView) -> Unit
) :
    PagingDataAdapter<Movie, MoviePagerAdapter.ViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), itemSelectedAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context)))
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.title == newItem.title
        }
    }

    class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie?, itemSelectedAction: (movie: Movie, imageView: ImageView) -> Unit) {
            binding.tvTitle.text = movie?.title ?: binding.root.context.getString(R.string.loading)
            if (movie?.posterPath?.isNotEmpty() == true) {
                binding.ivMovie.loadFromUrl(movie.getFullPosterPath())
            } else {
                binding.ivMovie.clearImageLoad()
            }
            binding.ivMovie.transitionName = movie?.title

            itemView.setOnClickListener {
                movie?.let { itemSelectedAction(it, binding.ivMovie) }
            }
        }
    }
}