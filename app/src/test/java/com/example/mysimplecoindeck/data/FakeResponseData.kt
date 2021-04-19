package com.example.mysimplecoindeck.data

import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.Data
import com.example.mysimplecoindeck.models.Stats
import com.example.mysimplecoindeck.models.searchSuggestions.SearchResponse
import com.example.mysimplecoindeck.models.singleCoin.AllTimeHigh
import com.example.mysimplecoindeck.models.singleCoin.Coin
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import com.example.mysimplecoindeck.models.singleCoin.Supply

object FakeResponseData {

    val coin = Coin("", AllTimeHigh("",0), "", "", "",
        "", "", "", listOf(), false, "", "", 0,
        0, "", 0, listOf(), Supply("",false,""), "",
        0, "uuid", "")

    private val coinRankingEntity = com.example.mysimplecoindeck.models.Coin("100","1","1","google.de","red",
        "google.de",4,false,"100","bitcon","40000",1,
        listOf(),"btc",1,"btc")

    val coinsResponse = CoinsResponse(Data(listOf(coinRankingEntity ),
    Stats(1,"",0,"",0)),
    "success")

    val singleCoinsResponse = CoinResponse(com.example.mysimplecoindeck.models.singleCoin.Data(coin),"success")

    val searchResponse = SearchResponse(data = com.example.mysimplecoindeck.models.searchSuggestions.Data(listOf(), listOf(), listOf()),"success")
}