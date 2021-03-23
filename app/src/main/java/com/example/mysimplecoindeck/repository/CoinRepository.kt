package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CoinRepository {
    val coinsList: Flow<Response<CoinsResponse>>

    fun coinDetail(uuid: String): Flow<Response<CoinResponse>>
}
