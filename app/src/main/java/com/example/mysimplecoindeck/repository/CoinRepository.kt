package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import com.example.mysimplecoindeck.models.searchSuggestions.SearchResponse
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CoinRepository {
    val coinsList: Flow<Response<CoinsResponse>>

    suspend fun coinDetail(uuid: String): Flow<Response<CoinResponse>>

    suspend fun getSearchSuggestions(query: String): Flow<Response<SearchResponse>>

    suspend fun upsert(portfolioEntity: CoinPortfolioEntity)

    suspend fun delete(portfolioEntity: CoinPortfolioEntity)

    fun getPortfolio(): Flow<List<CoinPortfolioEntity>>
}
