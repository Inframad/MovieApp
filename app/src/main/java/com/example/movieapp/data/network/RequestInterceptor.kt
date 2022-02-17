package com.example.movieapp.data.network

import com.example.movieapp.BuildConfig
import okhttp3.Interceptor
import javax.inject.Inject

class RequestInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api-key", BuildConfig.API_KEY)
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}