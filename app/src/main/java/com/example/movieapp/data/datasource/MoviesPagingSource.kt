package com.example.movieapp.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.MovieDTO
import com.example.movieapp.data.network.MoviesApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val api: MoviesApi,
) : PagingSource<Int, MovieDTO>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MovieDTO> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = api.getMoviesResponse(nextPageNumber)
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = nextPageNumber+20
            )
        } catch (e: IOException) {
            // IOException for network failures.
                Log.e("MoviesPagingSource", "IOException", e)
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            Log.e("MoviesPagingSource", "HTTPException", e)
            return LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, MovieDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
