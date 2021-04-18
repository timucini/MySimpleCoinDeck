package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.Data
import com.example.mysimplecoindeck.models.Stats
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import com.example.mysimplecoindeck.models.searchSuggestions.SearchResponse
import com.example.mysimplecoindeck.models.singleCoin.AllTimeHigh
import com.example.mysimplecoindeck.models.singleCoin.Coin
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import com.example.mysimplecoindeck.models.singleCoin.Supply
import com.example.mysimplecoindeck.utils.Mapper.mapToInsertEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAndroidCoinRepositoryImpl: CoinRepository {

    private val coinPortfolioEntities = mutableListOf<CoinPortfolioEntity>()
    private val coin = Coin(
            "",
            AllTimeHigh("",0),
            "",
            "",
            "",
            "",
            "",
            "",
            listOf(),
            false,
            "",
            "",
            0,
            0,
            "",
            0,
            listOf(),
            Supply("",false,""),
            "",
            0,
            "uuid",
            "")

    private var shouldReturnNetworkError = false

    /**
     * To Test when a network error occurs
     */
    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }


    override fun getCoinsRanking(): Flow<CoinsResponse> = flow {
        emit (CoinsResponse
        (Data(listOf(),
                Stats(1,"",0,"",0)),
                "success"))
    }

    override fun coinDetail(uuid: String): Flow<CoinResponse> = flow {
        emit(CoinResponse(com.example.mysimplecoindeck.models.singleCoin.Data(coin),"success"))
    }

    override fun getSearchSuggestions(query: String): Flow<SearchResponse> = flow {
        emit(SearchResponse(data = com.example.mysimplecoindeck.models.searchSuggestions.Data(listOf(), listOf(), listOf()),"success"))
    }

    override suspend fun upsert(coinPortfolioEntity: CoinPortfolioEntity) {
        coinPortfolioEntities.add(coinPortfolioEntity)
    }

    override fun getPortfolio(): Flow<List<CoinPortfolioEntity>> = flow {
        emit (coinPortfolioEntities)
    }
}