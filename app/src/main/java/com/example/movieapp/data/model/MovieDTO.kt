package com.example.movieapp.data.model

import com.squareup.moshi.Json

data class MovieDTO(
    @field:Json(name = "display_title") val title: String,
    @field:Json(name = "summary_short") val description: String,
    val multimedia: Multimedia
)

data class Multimedia(
    val type: String,
    val src: String,
    val height: Int,
    val width: Int
)