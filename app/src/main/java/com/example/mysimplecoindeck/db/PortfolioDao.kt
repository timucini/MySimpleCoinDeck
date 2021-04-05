package com.example.mysimplecoindeck.db

import androidx.room.*
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity

@Dao
interface PortfolioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(coinPortfolioEntity: CoinPortfolioEntity)

    @Query("SELECT * FROM CoinEntities")
    fun getAllPortfolioCoins(): List<CoinPortfolioEntity>

    @Delete
    fun deletePortfolioCoin(coinPortfolioEntity: CoinPortfolioEntity)
}