package com.example.mysimplecoindeck.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity

@Database(
        entities = [CoinPortfolioEntity::class],
        version = 1
)
abstract class PortfolioDatabase: RoomDatabase() {

    abstract fun getPortfolioDao(): PortfolioDao
}