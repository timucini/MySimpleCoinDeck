package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.data.FakeResponseDataAndroid
import com.example.mysimplecoindeck.data.FakeResponseDataAndroid.searchResponse
import com.example.mysimplecoindeck.data.FakeResponseDataAndroid.singleCoinsResponse
import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import com.example.mysimplecoindeck.models.searchSuggestions.SearchResponse
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAndroidCoinRepositoryImpl: CoinRepository {

    private val coinPortfolioEntities = mutableListOf<CoinPortfolioEntity>()
    private val shouldReturnNetworkError = false

    override fun getCoinsRanking(): Flow<CoinsResponse> = flow {
        if (shouldReturnNetworkError) {
            throw Exception()
        }
        emit(FakeResponseDataAndroid.coinsResponse)
    }

    override fun coinDetail(uuid: String): Flow<CoinResponse> = flow {
        if (shouldReturnNetworkError) {
            throw Exception()
        }
        emit(singleCoinsResponse)
    }

    override fun getSearchSuggestions(query: String): Flow<SearchResponse> = flow {
        if (shouldReturnNetworkError) {
            throw Exception()
        }
        emit(searchResponse)
    }

    override suspend fun upsert(coin: CoinPortfolioEntity) {
        if (shouldReturnNetworkError) {
            throw Exception()
        }
        coinPortfolioEntities.add(coin)
    }

    override fun getPortfolio(): Flow<List<CoinPortfolioEntity>> = flow {
        if (shouldReturnNetworkError) {
            throw Exception()
        }
        emit(coinPortfolioEntities)
    }
}