package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.api.CoinApi
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinApi: CoinApi
) : CoinRepository {
    override suspend fun getCoins() = coinApi.getCoinsList()

    override suspend fun getCoin(id: Int) = coinApi.getCoin(id)
}
