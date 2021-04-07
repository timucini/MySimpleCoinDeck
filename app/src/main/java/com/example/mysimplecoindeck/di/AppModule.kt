package com.example.mysimplecoindeck.di

import com.example.mysimplecoindeck.repository.CoinRepository
import com.example.mysimplecoindeck.repository.CoinRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideCoinRepository(coinRepositoryImpl: CoinRepositoryImpl): CoinRepository =
        coinRepositoryImpl

}

