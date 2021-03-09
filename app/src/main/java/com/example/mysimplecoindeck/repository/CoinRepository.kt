package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.api.RetroFitInstance

class CoinRepository(
) {
    suspend fun getCoins() = RetroFitInstance.api.getCoinsList()

    suspend fun getCoin(id: Int) = RetroFitInstance.api.getCoin(id)
}