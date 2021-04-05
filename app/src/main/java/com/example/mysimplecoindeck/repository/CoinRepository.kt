package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import com.example.mysimplecoindeck.models.searchSuggestions.SearchResponse
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import com.example.mysimplecoindeck.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

interface CoinRepository {
    val coinsList: Flow<Response<CoinsResponse>>

    fun coinDetail(uuid: String): Flow<Response<CoinResponse>>

    fun getSearchSuggestions(query: String): Flow<Response<SearchResponse>>

    suspend fun upsert(portfolioEntity: CoinPortfolioEntity)

    suspend fun delete(portfolioEntity: CoinPortfolioEntity)

    suspend fun getPortfolio(): Flow<List<CoinPortfolioEntity>>
}
