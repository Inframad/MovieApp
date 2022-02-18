package com.example.movieapp.presentation

import java.util.concurrent.atomic.AtomicInteger

data class Movie(
    val id: Int = autoincrement(),
    val title: String,
    val description: String,
    val imageUrl: String
) {
    companion object {
        private val id = AtomicInteger(0)
        private fun autoincrement() = id.getAndIncrement()
    }
}