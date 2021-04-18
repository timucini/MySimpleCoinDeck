package com.example.mysimplecoindeck.di

import android.content.Context
import androidx.room.Room
import com.example.mysimplecoindeck.db.PortfolioDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, PortfolioDatabase::class.java)
            .allowMainThreadQueries()
            .build()

}