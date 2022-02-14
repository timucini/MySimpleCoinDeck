package com.example.mysimplecoindeck.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysimplecoindeck.repository.CoinRepository
import androidx.lifecycle.viewModelScope
import com.example.mysimplecoindeck.models.CoinsResponse
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import com.example.mysimplecoindeck.models.searchSuggestions.SearchResponse
import com.example.mysimplecoindeck.models.singleCoin.Coin
import com.example.mysimplecoindeck.models.singleCoin.CoinResponse
import com.example.mysimplecoindeck.utils.Event
import com.example.mysimplecoindeck.utils.Mapper.mapToInsertEntity
import com.example.mysimplecoindeck.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<CoinPortfolioEntity>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<CoinPortfolioEntity>>> = _insertShoppingItemStatus

    init {
        getCoinsRankingList()
    }

    fun getCoinsRankingList() {
        viewModelScope.launch {
            repository.getCoinsRanking()
                    .catch {  exception ->
                        _uiState.value = CoinsListUiState.Error(exception)
                    }
                    .collect{ coinsResponse ->
                        _uiState.value = CoinsListUiState.Success(coinsResponse)
                    }
        }
    }

    fun getCoinDetails(uuid: String) {
        if (uuid.isEmpty()) {
            _uiCoinDetailState.value = CoinDetailUiState.Error(Throwable("Amount must not be empty"))
            return
        }
        viewModelScope.launch {
            repository.coinDetail(uuid)
                    .catch { exception ->
                        _uiCoinDetailState.value = CoinDetailUiState.Error(exception)
                    }
                    .collect{ coinDetail ->
                        _uiCoinDetailState.value = CoinDetailUiState.Success(coinDetail)
                    }
        }
    }

    fun getSuggestedCoins(query: String) {
        viewModelScope.launch {
            repository.getSearchSuggestions(query)
                .catch { exception ->
                    _uiSearchState.value = SearchUiState.Error(exception)
                }
                .collect{ response ->
                    _uiSearchState.value = SearchUiState.Success(response)
                }
        }
    }

    fun getPortfolio() {
        viewModelScope.launch {
            val flow = repository.getPortfolio()
                    .catch { exception ->
                        _uiPortfolioState.value = PortfolioUiState.Error(exception)
                    }
                    flow.collect{ response ->
                        _uiPortfolioState.value = PortfolioUiState.Success(response)
                    }
        }
    }

    fun insertCoinToPortfolioIntoDb(coinPortfolioEntity: CoinPortfolioEntity) {
        viewModelScope.launch {
                repository.upsert(coinPortfolioEntity)
        }
    }

    fun insertCoin(coin: Coin, input: String) {
        if(input.isEmpty()) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("Amount must not be empty",null)))
            return
        }
        try {
            input.toInt()
        } catch (e: Exception) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("Please enter a valid amount",null)))
            return
        }
        val portfolioEntity = coin.mapToInsertEntity(input)
        insertCoinToPortfolioIntoDb(portfolioEntity)
        _insertShoppingItemStatus.postValue(Event(Resource.success(portfolioEntity)))
    }



    sealed class CoinsListUiState {
        data class Success(val coins: CoinsResponse): CoinsListUiState()
        data class Error(val exception: Throwable): CoinsListUiState()
        object Empty : CoinsListUiState()
    }

    sealed class CoinDetailUiState {
        data class Success(val coins: CoinResponse): CoinDetailUiState()
        data class Error(val exception: Throwable): CoinDetailUiState()
        object Empty : CoinDetailUiState()
    }

    sealed class SearchUiState {
        data class Success(val coinSuggestions: SearchResponse): SearchUiState()
        data class Error(val exception: Throwable): SearchUiState()
        object Empty : SearchUiState()
    }

    sealed class PortfolioUiState {
        data class Success(val portfolio: List<CoinPortfolioEntity>): PortfolioUiState()
        data class Error(val exception: Throwable): PortfolioUiState()
        object Empty : PortfolioUiState()
    }
}