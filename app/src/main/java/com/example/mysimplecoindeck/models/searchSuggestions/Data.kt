package com.example.mysimplecoindeck.models.searchSuggestions

data class Data(
    val coins: List<Coin>,
    val exchanges: List<Exchange>,
    val markets: List<Market>
)