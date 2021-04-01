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
import retrofit2.Response
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinApi: CoinApi,
    private val portfolioDao: PortfolioDao
) : CoinRepository {
    override val coinsList: Flow<Response<CoinsResponse>> = flow {
        while (true) {
            val coinsList = coinApi.getCoinsList()
            emit(coinsList)
            delay(Constants.REFRESHINTERVALMS)
        }
    }

    override fun coinDetail(uuid: String): Flow<Response<CoinResponse>> = flow {
        while (true) {
            val coinDetail = coinApi.getCoin(uuid)
            emit(coinDetail)
            delay(Constants.REFRESHINTERVALMS)
        }
    }

    override fun getSearchSuggestions(query: String): Flow<Response<SearchResponse>> = flow {
        while(true) {
            val searchSuggestions = coinApi.getSearchSuggestions(query)
            emit(searchSuggestions)
            delay(Constants.SEARCH_QUERY_TIME_DELAY)
        }
    }
    override suspend fun upsert(portfolioEntity: CoinPortfolioEntity) = portfolioDao.upsert(portfolioEntity)

    override suspend fun delete(portfolioEntity: CoinPortfolioEntity) = portfolioDao.deletePortfolioCoin(portfolioEntity)

    override fun getPortfolio(): Flow<List<CoinPortfolioEntity>> = flow {
        while(true) {
            val portfolio = portfolioDao.getAllPortfolioCoins()
            emit(portfolio)
            delay(Constants.REFRESHINTERVALMS)
        }
    }
}
