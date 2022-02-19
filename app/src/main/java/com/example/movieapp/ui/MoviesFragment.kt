package com.example.movieapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.movieapp.App
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMoviesBinding
import com.example.movieapp.presentation.Movie
import com.example.movieapp.presentation.MovieFragmentViewModel
import com.example.movieapp.presentation.NetworkState
import com.example.movieapp.ui.adapter.MoviesLoadStateAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding: FragmentMoviesBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieFragmentViewModel

    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[MovieFragmentViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingAdapter = MoviesAdapter(MovieComparator)


        binding.rv.adapter = pagingAdapter
            .withLoadStateFooter(footer = MoviesLoadStateAdapter {
                pagingAdapter.retry()
            })

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moviesFlow.collectLatest { pagingData: PagingData<Movie> ->
                pagingAdapter.submitData(pagingData)
            }
        }

        viewModel.networkState.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    NetworkState.Available -> {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.connection_restored_msg),
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                        pagingAdapter.retry()
                    }
                    NetworkState.NotAvailable -> {
                        errorSnackbar = Snackbar.make(
                            binding.root,
                            getString(R.string.check_connection_msg),
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .setAction(getString(R.string.dismiss_msg)) {
                                errorSnackbar?.dismiss()
                            }
                        errorSnackbar?.show()
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        errorSnackbar?.dismiss()
        errorSnackbar = null
        _binding = null
    }
}