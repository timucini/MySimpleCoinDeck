package com.example.mysimplecoindeck.db

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    fun providePortfolioDao(portfolioDatabase: PortfolioDatabase): PortfolioDao =
            portfolioDatabase.getPortfolioDao()

}
