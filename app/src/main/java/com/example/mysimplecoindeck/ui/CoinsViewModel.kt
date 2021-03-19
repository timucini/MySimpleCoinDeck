package com.example.mysimplecoindeck.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysimplecoindeck.repository.CoinRepository
import androidx.lifecycle.viewModelScope
import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
        private val repository: CoinRepository
) : ViewModel() {

    val coinsList: MutableLiveData<Resource<Response<CoinsResponse>>> = MutableLiveData()

    init {
        // just for testing api atm
        getCoins()
    }

    private fun getCoins() = viewModelScope.launch {
        try {
            repository.coinList.collect{ coinsResponse ->
                coinsList.postValue(Resource.Success(coinsResponse))
                Log.d("API-Test passed",coinsResponse.body()?.status.toString())
            }
        } catch (ex: Exception) {
            Log.d("API-Test","error while call api: ${ex.message}")
        }
    }
}