package com.example.mysimplecoindeck.api

import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.searchSuggestions.SearchResponse
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinApi {

    @GET("coins")
    suspend fun getCoinsList(): CoinsResponse


    @GET("coin/{id}")
    suspend fun getCoin(
        @Path("id")
        uuid: String
    ): CoinResponse

    @GET("search-suggestions")
    suspend fun getSearchSuggestions(
        @Query("query")
        searchQuery: String
    ): SearchResponse
}
