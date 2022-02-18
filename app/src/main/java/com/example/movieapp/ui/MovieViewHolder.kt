package com.example.movieapp.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.presentation.Movie

class MovieViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val circularProgressDrawable = CircularProgressDrawable(binding.root.context)

    init {
        circularProgressDrawable.apply {
            strokeWidth = 5f
            centerRadius = 30f
        }.start()
    }

    fun bind(movie: Movie?) {
        if (movie != null && !movie.isPlaceholder) {
            binding.apply {
                hidePlaceholders()
                movieTitleTv.text = movie.title
                movieDescTv.text = movie.description
                Glide.with(root)
                    .load(movie.imageUrl)
                    .centerCrop()
                    .placeholder(circularProgressDrawable)
                    .into(movieIv)
            }
        } else {
            binding.apply {
                hidePlaceholders(false)
            }
        }
    }

    private fun hidePlaceholders(bool: Boolean = true) {
        if (!bool) {
            binding.apply {
                shimmerLayout.startShimmer()
                movieTitleTvPlaceholder.visibility = View.VISIBLE
                movieDescTvPlaceholder.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                shimmerLayout.stopShimmer()
                shimmerLayout.hideShimmer()
                movieTitleTvPlaceholder.visibility = View.GONE
                movieDescTvPlaceholder.visibility = View.GONE
            }
        }

    }

}
