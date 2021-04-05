package com.example.mysimplecoindeck.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mysimplecoindeck.repository.CoinRepository
import androidx.lifecycle.viewModelScope
import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import com.example.mysimplecoindeck.models.searchSuggestions.SearchResponse
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

    private val _uiSearchState = MutableStateFlow<SearchUiState>(SearchUiState.Empty)
    val uiSearchState: StateFlow<SearchUiState> = _uiSearchState

    private val _uiPortfolioState = MutableStateFlow<PortfolioUiState>(PortfolioUiState.Empty)
    val uiPortfolioState: StateFlow<PortfolioUiState> = _uiPortfolioState

    init {
        viewModelScope.launch {
            repository.coinsList
                    .catch { exception ->
                        _uiState.value = CoinsListUiState.Error(exception)
                        Log.d("GetCoinsList","error while call api: ${exception.message}")
                    }
                    .collect{ coinsResponse ->
                        _uiState.value = CoinsListUiState.Success(coinsResponse)
                        Log.d("GetCoinsList passed",coinsResponse.body()?.status.toString())
                    }
        }
    }

    fun getCoinDetails(uuid: String) {
        viewModelScope.launch {
            repository.coinDetail(uuid)
                    .catch { exception ->
                        _uiCoinDetailState.value = CoinDetailUiState.Error(exception)
                        Log.d("GetCoinDetails","error while call api: ${exception.message}")
                    }
                    .collect{ coinDetail ->
                        _uiCoinDetailState.value = CoinDetailUiState.Success(coinDetail)
                        Log.d("GetCoinDetails passed",coinDetail.body()?.status.toString())
                    }
        }
    }

    fun getSuggestedCoins(query: String) {
        viewModelScope.launch {
            repository.getSearchSuggestions(query)
                .catch { exception ->
                    _uiSearchState.value = SearchUiState.Error(exception)
                    Log.d("GetSuggestions","error while call api: ${exception.message}")
                }
                .collect{ response ->
                    _uiSearchState.value = SearchUiState.Success(response)
                    Log.d("GetSuggestions passed",response.body()?.status.toString())
                }
        }
    }

    fun getPortfolio() {
        viewModelScope.launch {
            repository.getPortfolio()
                    .catch { exception ->
                        _uiPortfolioState.value = PortfolioUiState.Error(exception)
                        Log.d("Portfolio-DB","error while getting Portfolio: ${exception.message}")
                    }
                    .collect{ response ->
                        _uiPortfolioState.value = PortfolioUiState.Success(response)
                        Log.d("Portfolio-DB passed:", "Successfully got portfolio")
                    }
        }
    }

    fun insertCoinToPortfolio(coinPortfolioEntity: CoinPortfolioEntity) {
        Log.d("Insert Coin in Portfolio:", "Try to insert Coin in Portfolio")
        viewModelScope.launch {
                repository.upsert(coinPortfolioEntity)
        }
    }

    fun deleteCoinFromPortfolio(coinPortfolioEntity: CoinPortfolioEntity) {
        viewModelScope.launch {
            repository.delete(coinPortfolioEntity)
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

    sealed class SearchUiState {
        data class Success(val coinSuggestions: Response<SearchResponse>): SearchUiState()
        data class Error(val exception: Throwable): SearchUiState()
        object Empty : SearchUiState()
    }

    sealed class PortfolioUiState {
        data class Success(val portfolio: List<CoinPortfolioEntity>): PortfolioUiState()
        data class Error(val exception: Throwable): PortfolioUiState()
        object Empty : PortfolioUiState()
    }
}