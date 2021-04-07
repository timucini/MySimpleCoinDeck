package com.example.mysimplecoindeck.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DbModule {

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
            ).build()
}
