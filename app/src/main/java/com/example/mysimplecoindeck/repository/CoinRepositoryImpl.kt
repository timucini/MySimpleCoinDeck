package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.api.CoinApi
import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import com.example.mysimplecoindeck.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinApi: CoinApi
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
}
