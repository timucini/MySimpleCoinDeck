package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.models.CoinResponse
import com.example.mysimplecoindeck.models.CoinsResponse
import retrofit2.Response

interface CoinRepository {
    suspend fun getCoins(): Response<CoinsResponse>

    suspend fun getCoin(id: Int): Response<CoinResponse>
}
