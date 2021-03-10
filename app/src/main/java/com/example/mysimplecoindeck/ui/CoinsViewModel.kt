package com.example.mysimplecoindeck.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mysimplecoindeck.repository.CoinRepository
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
        private val repository: CoinRepository
) : ViewModel() {

    init {
        // just for testing api atm
        getCoins()
    }

    private fun getCoins() = viewModelScope.launch {
        try {
            val response = repository.getCoins()
            Log.d("API-Test passed",response.body()?.status.toString())
        } catch (ex: Exception) {
            Log.d("API-Test","error while call api: ${ex.message}")
        }
    }
}