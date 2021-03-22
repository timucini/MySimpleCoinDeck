package com.example.mysimplecoindeck.api

import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApi {

    @GET("coins")
    suspend fun getCoinsList(): Response<CoinsResponse>


    @GET("coin/{id}")
    suspend fun getCoin(
        @Path("id")
        uuid: String
    ): Response<CoinResponse>
}
