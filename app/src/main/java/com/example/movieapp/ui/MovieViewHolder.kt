package com.example.movieapp.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.presentation.Movie

class MovieViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.apply {
            movieTitleTv.text = movie.title
            movieDescTv.text = movie.description
        }
    }

}
