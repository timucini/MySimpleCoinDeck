package com.example.mysimplecoindeck.di

import com.example.mysimplecoindeck.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    @Named("repository")
    fun provideRepository() = CoinRepository()
}