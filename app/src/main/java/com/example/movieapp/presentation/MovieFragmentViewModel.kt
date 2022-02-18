package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movieapp.data.repository.Repository
import javax.inject.Inject

class MovieFragmentViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    val moviesFlow = repository.moviesFlow.cachedIn(viewModelScope)
}