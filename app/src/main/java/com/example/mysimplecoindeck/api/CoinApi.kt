package com.example.mysimplecoindeck.api

import com.example.mysimplecoindeck.models.CoinResponse
import com.example.mysimplecoindeck.models.CoinsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface CoinApi {
    @Headers(
        "x-rapidapi-host: coinranking1.p.rapidapi.com"
    )
    @GET("coins")
    suspend fun getCoinsList(): Response<CoinsResponse>

    @Headers(
        "x-rapidapi-host: coinranking1.p.rapidapi.com"
    )
    @GET("coin/{id}")
    suspend fun getCoin(
        @Path("id")
        id: Int
    ): Response<CoinResponse>
}
