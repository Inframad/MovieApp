package com.example.movieapp.data.datasource

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
                nextKey = nextPageNumber + 20
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            val pageIndex = state.pages.indexOf(anchorPage)
            if (pageIndex == 0) return null
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
