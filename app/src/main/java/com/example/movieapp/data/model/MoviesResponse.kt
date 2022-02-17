package com.example.movieapp.data.model

import com.squareup.moshi.Json

data class MoviesResponse(
    val status: String,
    val copyright: String,
    @field:Json(name = "has_more") val hasMore: Boolean,
    val results: List<MovieDTO>
)
