package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.models.CoinResponse
import com.example.mysimplecoindeck.models.CoinsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CoinRepository {
    val coinList: Flow<Response<CoinsResponse>>
}
