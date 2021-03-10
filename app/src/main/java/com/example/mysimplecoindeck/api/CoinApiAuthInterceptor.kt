package com.example.mysimplecoindeck.api

import com.example.mysimplecoindeck.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class CoinApiAuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = BuildConfig.COIN_RANKING_API_KEY
        val original = chain.request();

        val request = original.newBuilder()
            .header("x-rapidapi-key", apiKey)
            .method(method = original.method, body = original.body)
            .build()

        return chain.proceed(request)
    }
}
