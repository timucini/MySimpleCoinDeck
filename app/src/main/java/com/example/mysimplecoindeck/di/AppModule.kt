package com.example.mysimplecoindeck.di

import com.example.mysimplecoindeck.adapters.CoinsAdapter
import com.example.mysimplecoindeck.adapters.PortfolioAdapter
import com.example.mysimplecoindeck.adapters.SearchAdapter
import com.example.mysimplecoindeck.repository.CoinRepository
import com.example.mysimplecoindeck.repository.CoinRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideCoinRepository(coinRepositoryImpl: CoinRepositoryImpl): CoinRepository =
        coinRepositoryImpl

    @Singleton
    @Provides
    fun provideCoinsAdapter(): CoinsAdapter = CoinsAdapter()

    @Singleton
    @Provides
    fun provideSearchAdapter(): SearchAdapter = SearchAdapter()

    @Singleton
    @Provides
    fun providePortfolioAdapter(): PortfolioAdapter = PortfolioAdapter()

    @Singleton
    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.Default

}

