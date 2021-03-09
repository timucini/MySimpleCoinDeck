package com.example.mysimplecoindeck.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mysimplecoindeck.repository.CoinRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception


class CoinsViewModel(
    private val repository: CoinRepository
) : ViewModel() {
    init {
        getCoins()
    }
    private fun getCoins() = viewModelScope.launch {
        try {
            val response = repository.getCoins()
            Log.d("API-Test",response.body()?.status.toString())
        } catch (ex: Exception) {
            Log.d("API-Test","error while api-call: ${ex.message}")
        }
    }
}