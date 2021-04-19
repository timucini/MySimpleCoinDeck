package com.example.mysimplecoindeck.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mysimplecoindeck.MainCoroutineRule
import com.example.mysimplecoindeck.data.FakeResponseData.coin
import com.example.mysimplecoindeck.data.FakeResponseData.coinsResponse
import com.example.mysimplecoindeck.data.FakeResponseData.singleCoinsResponse
import com.example.mysimplecoindeck.getOrAwaitValueTest
import com.example.mysimplecoindeck.repository.FakeCoinRepositoryImpl
import com.example.mysimplecoindeck.utils.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CoinsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CoinsViewModel

    @Before
    fun setup() {
        viewModel = CoinsViewModel(FakeCoinRepositoryImpl(false))
    }


    @Test
    fun `insert coin portfolio item with empty field, returns error`() {
        viewModel.insertCoin(coin,"")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert coin portfolio item with not numeric input, returns error`() {
        viewModel.insertCoin(coin,"test")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert coin portfolio item with valid input, returns success`() {
        viewModel.insertCoin(coin,"10")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `insert coin details with valid input, returns success`() = runBlockingTest {
        viewModel.getCoinDetails("uuid")
        val value = viewModel.uiCoinDetailState.value

        assertThat(value).isEqualTo(CoinsViewModel.CoinDetailUiState.Success(singleCoinsResponse))
    }

    @Test
    fun `insert coin details with invalid input, returns error`() = runBlockingTest {
        viewModel.getCoinDetails("")
        val value = viewModel.uiCoinDetailState.value

        assertThat(value).isInstanceOf(CoinsViewModel.CoinDetailUiState.Error::class.java)
    }

    @Test
    fun `should get successfully get CoinRanking`() = runBlockingTest {
        viewModel.getCoinsRankingList()
        val value = viewModel.uiState.value

        assertThat(value).isEqualTo(CoinsViewModel.CoinsListUiState.Success(coinsResponse))
    }

    @Test
    fun `test error handling while getting CoinsRankingList`() = runBlockingTest {
        val viewModelException = CoinsViewModel(FakeCoinRepositoryImpl(true))
        viewModelException.getCoinsRankingList()
        val value = viewModelException.uiState.value

        assertThat(value).isInstanceOf(CoinsViewModel.CoinsListUiState.Error::class.java)
    }

    @Test
    fun `test error handling while getting CoinDetail`() = runBlockingTest {
        val viewModelException = CoinsViewModel(FakeCoinRepositoryImpl(true))
        viewModelException.getCoinDetails("uuid")
        val value = viewModelException.uiCoinDetailState.value

        assertThat(value).isInstanceOf(CoinsViewModel.CoinDetailUiState.Error::class.java)
    }
}