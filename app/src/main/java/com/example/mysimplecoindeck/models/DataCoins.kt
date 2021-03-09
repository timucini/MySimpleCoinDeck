package com.example.mysimplecoindeck.models

data class DataCoins(
    val base: Base,
    val coins: List<Coin>,
    val stats: Stats
)