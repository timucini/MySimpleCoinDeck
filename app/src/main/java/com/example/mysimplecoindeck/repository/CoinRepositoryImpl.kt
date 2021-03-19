package com.example.mysimplecoindeck.repository

import com.example.mysimplecoindeck.api.CoinApi
import com.example.mysimplecoindeck.api.CoinRemoteDataSource
import com.example.mysimplecoindeck.models.CoinsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import retrofit2.Response
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    coinRemoteDataSource: CoinRemoteDataSource
) : CoinRepository {
    override val coinList: Flow<Response<CoinsResponse>> =
            coinRemoteDataSource.coinsList
}
