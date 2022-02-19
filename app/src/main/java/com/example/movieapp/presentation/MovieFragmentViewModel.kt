package com.example.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movieapp.data.repository.Repository
import com.example.movieapp.presentation.util.SingleLiveEvent
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieFragmentViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    val moviesFlow = repository.moviesFlow.cachedIn(viewModelScope)

    private val _networkState: SingleLiveEvent<NetworkState> = SingleLiveEvent()
    val networkState: LiveData<NetworkState> = _networkState

    init {
        viewModelScope.launch {
            repository.networkConnectionFlow.collectIndexed { index, value ->
                when(index) {
                    0 -> if(value == null) _networkState.value = NetworkState.NotAvailable
                    else -> value?.let {
                        _networkState.value = if(value) NetworkState.Available else NetworkState.NotAvailable
                    }
                }
            }
        }
    }

}