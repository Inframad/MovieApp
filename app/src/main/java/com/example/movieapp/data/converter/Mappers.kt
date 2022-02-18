package com.example.movieapp.data.converter

import com.example.movieapp.data.model.MovieDTO
import com.example.movieapp.presentation.Movie

fun MovieDTO.toMovie(): Movie =
    Movie(
        title = title,
        description = description,
        imageUrl = multimedia.src
    )