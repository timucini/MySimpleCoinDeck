package com.example.mysimplecoindeck.models.searchSuggestions

data class Market(
    val baseSymbol: String,
    val baseUuid: String,
    val exchangeIconUrl: String,
    val exchangeName: String,
    val exchangeUuid: String,
    val quoteSymbol: String,
    val quoteUuid: String,
    val uuid: String
)