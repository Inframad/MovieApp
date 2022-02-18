package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.movieapp.data.converter.toMovie
import com.example.movieapp.data.datasource.MoviesPagingSource
import com.example.movieapp.data.network.MoviesApi
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Repository @Inject constructor(private val api: MoviesApi) {

    val moviesFlow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        MoviesPagingSource(api)
    }.flow.map {
        it.map { movieDTO -> movieDTO.toMovie() }
    }

}