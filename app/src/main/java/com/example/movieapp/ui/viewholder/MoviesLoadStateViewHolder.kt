package com.example.movieapp.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.MoviesLoadStateFooterViewItemBinding
import retrofit2.HttpException
import java.io.IOException

class MoviesLoadStateViewHolder(
    private val binding: MoviesLoadStateFooterViewItemBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = when (loadState.error) {
                is HttpException -> when ((loadState.error as HttpException).code()) {
                    429 -> binding.root.context.getString(R.string.fast_scrolling_error_msg)
                    401 -> binding.root.context.getString(R.string.check_connection_msg)
                    else -> loadState.error.localizedMessage
                }
                is IOException -> binding.root.context.getString(R.string.check_connection_msg)
                else -> loadState.error.localizedMessage
            }
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): MoviesLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movies_load_state_footer_view_item, parent, false)
            val binding = MoviesLoadStateFooterViewItemBinding.bind(view)
            return MoviesLoadStateViewHolder(binding, retry)
        }
    }
}
