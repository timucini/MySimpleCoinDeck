package com.example.mysimplecoindeck.utils

import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import com.example.mysimplecoindeck.models.singleCoin.Coin

object Mapper {
        fun Coin.mapToInsertEntity(amount: String): CoinPortfolioEntity {
            return CoinPortfolioEntity(
                    uuid = this.uuid,
                    name = this.name,
                    iconUrl = this.iconUrl,
                    price = this.price,
                    change = this.change,
                    amount = amount)
        }
}