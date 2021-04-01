package com.example.mysimplecoindeck.api

import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.searchSuggestions.SearchResponse
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinApi {

    @GET("coins")
    suspend fun getCoinsList(): Response<CoinsResponse>


    @GET("coin/{id}")
    suspend fun getCoin(
        @Path("id")
        uuid: String
    ): Response<CoinResponse>

    @GET("search-suggestions")
    suspend fun getSearchSuggestions(
        @Query("query")
        searchQuery: String
    ): Response<SearchResponse>
}
