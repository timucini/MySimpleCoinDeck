package com.example.mysimplecoindeck.api

import com.example.mysimplecoindeck.models.CoinsResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class CoinRemoteDataSource @Inject constructor(
        private val coinApi: CoinApi
) {
    private val refreshIntervalMS: Long = 500

    val coinsList: Flow<Response<CoinsResponse>> = flow {
        while (true) {
            val coinsList = coinApi.getCoinsList()
            emit(coinsList)
            delay(refreshIntervalMS)
        }
    }
}