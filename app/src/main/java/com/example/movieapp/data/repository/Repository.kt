package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movieapp.data.converter.toMovie
import com.example.movieapp.data.datasource.MoviesPagingSource
import com.example.movieapp.data.network.MoviesApi
import com.example.movieapp.data.network.NetworkConnectionChecker
import com.example.movieapp.presentation.Movie
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: MoviesApi,
    networkChecker: NetworkConnectionChecker
) {

    val moviesFlow = Pager(
        PagingConfig(pageSize = 20, enablePlaceholders = true)
    ) {
        MoviesPagingSource(api)
    }.flow.map {
        it.map { movieDTO -> movieDTO.toMovie() }
    }.onStart {
        emit(PagingData.from(List(4) {
            Movie(
                title = "",
                description = "",
                imageUrl = "",
                isPlaceholder = true
            )
        }))
    }

    val networkConnectionFlow = networkChecker.networkIsAvailable
}