package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.models.singleCoin.Coin
import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import com.example.mysimplecoindeck.models.searchSuggestions.SearchResponse
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CoinRepository {
    val coinsList: Flow<CoinsResponse>

    fun coinDetail(uuid: String): Flow<CoinResponse>

    fun getSearchSuggestions(query: String): Flow<SearchResponse>

    suspend fun upsert(coin: Coin, amount: String)

    fun getPortfolio(): Flow<List<CoinPortfolioEntity>>
}
