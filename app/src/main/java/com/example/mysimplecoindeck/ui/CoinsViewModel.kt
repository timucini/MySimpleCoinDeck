package com.example.mysimplecoindeck.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mysimplecoindeck.repository.CoinRepository
import androidx.lifecycle.viewModelScope
import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
        private val repository: CoinRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CoinsListUiState>(CoinsListUiState.Empty)
    val uiState: StateFlow<CoinsListUiState> = _uiState

    private val _uiCoinDetailState = MutableStateFlow<CoinDetailUiState>(CoinDetailUiState.Empty)
    val uiCoinDetailState: StateFlow<CoinDetailUiState> = _uiCoinDetailState

    init {
        viewModelScope.launch {
            repository.coinsList
                    .catch { exception ->
                        _uiState.value = CoinsListUiState.Error(exception)
                        Log.d("API-Test","error while call api: ${exception.message}")
                    }
                    .collect{ coinsResponse ->
                        _uiState.value = CoinsListUiState.Success(coinsResponse)
                        Log.d("API-Test passed",coinsResponse.body()?.status.toString())
                    }
        }
    }

    fun getCoinDetails(uuid: String) {
        viewModelScope.launch {
            repository.coinDetail(uuid)
                    .catch { exception ->
                        _uiCoinDetailState.value = CoinDetailUiState.Error(exception)
                        Log.d("API-Test","error while call api: ${exception.message}")
                    }
                    .collect{ coinDetail ->
                        _uiCoinDetailState.value = CoinDetailUiState.Success(coinDetail)
                        Log.d("API-Test passed",coinDetail.body()?.status.toString())
                    }
        }
    }

    sealed class CoinsListUiState {
        data class Success(val coins: Response<CoinsResponse>): CoinsListUiState()
        data class Error(val exception: Throwable): CoinsListUiState()
        object Empty : CoinsListUiState()
    }

    sealed class CoinDetailUiState {
        data class Success(val coins: Response<CoinResponse>): CoinDetailUiState()
        data class Error(val exception: Throwable): CoinDetailUiState()
        object Empty : CoinDetailUiState()
    }
}