package com.example.mysimplecoindeck.models.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.math.BigDecimal

@Entity(
        tableName = "CoinEntities"
)
data class CoinPortfolioEntity(
        @PrimaryKey
        var uuid: String,
        var name: String,
        var iconUrl: String,
        var price: String,
        var change: String,
        var amount: String
): Serializable
