package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.api.RetroFitInstance
import javax.inject.Inject

class CoinRepository @Inject constructor(
) {
    suspend fun getCoins() = RetroFitInstance.api.getCoinsList()

    suspend fun getCoin(id: Int) = RetroFitInstance.api.getCoin(id)
}