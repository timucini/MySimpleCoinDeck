package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.api.CoinApi
import com.example.mysimplecoindeck.db.PortfolioDao
import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import com.example.mysimplecoindeck.models.searchSuggestions.SearchResponse
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import com.example.mysimplecoindeck.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
        private val coinApi: CoinApi,
        private val portfolioDao: PortfolioDao
) : CoinRepository {
    override fun getCoinsRanking(): Flow<CoinsResponse> = flow {
            val coinsList = coinApi.getCoinsList()
            emit(coinsList)
            delay(Constants.REFRESHINTERVALMS)
    }

    override fun coinDetail(uuid: String): Flow<CoinResponse> {
        return flow {
            val coinDetail = coinApi.getCoin(uuid)
            emit(coinDetail)
            delay(Constants.REFRESHINTERVALMS)
        }
    }

    override fun getSearchSuggestions(query: String): Flow<SearchResponse> = flow {
        while(true) {
            val searchSuggestions = coinApi.getSearchSuggestions(query)
            emit(searchSuggestions)
            delay(Constants.REFRESHINTERVALMS)
        }
    }

    override suspend fun upsert(coin: CoinPortfolioEntity) = portfolioDao.upsert(coin)

    //override suspend fun delete(coin: Coin) = portfolioDao.deletePortfolioCoin(coin)

    override fun getPortfolio(): Flow<List<CoinPortfolioEntity>>  {
        return flow {
            val portfolio = portfolioDao.getAllPortfolioCoins()
            emit(portfolio)
            delay(Constants.REFRESHINTERVALMS)
        }
    }

}
