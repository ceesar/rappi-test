package com.rappi.testapp.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rappi.testapp.databinding.ItemNetworkStateBinding

class MovieLoadStateAdapter(
    private val adapter: MoviePagerAdapter
) : LoadStateAdapter<MovieLoadStateAdapter.NetworkStateItemViewHolder>() {

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, loadState: LoadState
    ): NetworkStateItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NetworkStateItemViewHolder(ItemNetworkStateBinding.inflate(inflater)) {
            adapter.retry()
        }
    }

    class NetworkStateItemViewHolder(
        private val binding: ItemNetworkStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading

                btnRetry.isVisible = loadState is LoadState.Error
                btnRetry.setOnClickListener { retryCallback() }

                val errorMsg = (loadState as? LoadState.Error)?.error?.message
                tvError.isVisible = !errorMsg.isNullOrBlank()
                tvError.text = errorMsg
            }
        }
    }
}