package com.example.movieapp.data.network

import com.example.movieapp.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("all.json")
    suspend fun getMoviesResponse(
        @Query("offset") page: Int
    ): MoviesResponse

}