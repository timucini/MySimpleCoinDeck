package com.example.mysimplecoindeck.di

import android.content.Context
import androidx.room.Room
import com.example.mysimplecoindeck.db.PortfolioDao
import com.example.mysimplecoindeck.db.PortfolioDatabase
import com.example.mysimplecoindeck.repository.CoinRepository
import com.example.mysimplecoindeck.repository.CoinRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun providePortfolioDao(portfolioDatabase: PortfolioDatabase): PortfolioDao =
            portfolioDatabase.getPortfolioDao()

    @Singleton
    @Provides
    fun providePortfolioDatabase(@ApplicationContext appContext: Context) =
            Room.databaseBuilder(
                    appContext.applicationContext,
                    PortfolioDatabase::class.java,
                    "portfolio_db.db"
            ).allowMainThreadQueries().build()
}

