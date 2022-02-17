package com.example.movieapp.di.data.network

import com.example.movieapp.data.network.MoviesApi
import com.example.movieapp.data.network.RequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        const val BASE_URL = "https://api.nytimes.com/svc/movies/v2/reviews/"
    }

    @Provides
    @Singleton
    fun provideClient(interceptor: RequestInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(moshiConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideMoviesApi(
        retrofit: Retrofit,
    ): MoviesApi =
        retrofit.create(MoviesApi::class.java)
}