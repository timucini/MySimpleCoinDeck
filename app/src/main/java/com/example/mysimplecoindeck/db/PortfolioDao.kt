package com.example.mysimplecoindeck.db

import androidx.room.*
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity

@Dao
interface PortfolioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(coinPortfolioEntity: CoinPortfolioEntity)

    @Query("SELECT * FROM CoinEntities")
    suspend fun getAllPortfolioCoins(): List<CoinPortfolioEntity>

    @Delete
    suspend fun deletePortfolioCoin(coinPortfolioEntity: CoinPortfolioEntity)
}