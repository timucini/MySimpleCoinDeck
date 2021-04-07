package com.example.mysimplecoindeck.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.databinding.FragmentCoinDetailBinding
import com.example.mysimplecoindeck.models.singleCoin.Coin
import com.example.mysimplecoindeck.ui.CoinsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import java.math.RoundingMode

@AndroidEntryPoint
class SingleCoinFragment: Fragment(R.layout.fragment_coin_detail) {
    private val viewModel: CoinsViewModel by viewModels()
    private lateinit var binding: FragmentCoinDetailBinding
    private val args: SingleCoinFragmentArgs by navArgs()
    private lateinit var coin: Coin
    private var uiDetailStateJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCoinDetailBinding.bind(view)
        viewModel.getCoinDetails(args.Uuid)
        uiDetailStateJob = lifecycleScope.launchWhenStarted {
            viewModel.uiCoinDetailState.collect {
                when(it) {
                    is CoinsViewModel.CoinDetailUiState.Success -> {
                        it.coins.let { coinsDetailResponse ->
                             coinsDetailResponse.data.coin.let {
                                 coinResponse ->
                                 coin = coinResponse
                                     with(binding) {
                                         tvCoinDetailTitle.text = coin.name
                                         tvCoinDetailPrice.text = coin.price.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toString()
                                         tvCoinDetailBtcPrice.text = coin.btcPrice
                                         tvCoinDetailRank.text = coin.rank.toString()
                                         tvCoinDetailCirculatingSupply.text = coin.supply.circulating
                                         tvCoinDetailAllTimeHigh.text = coin.allTimeHigh.price.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toString()
                                         tvCoinDetailMarketCap.text = coin.marketCap.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toString()
                                     }
                            }
                        }
                    }
                    is CoinsViewModel.CoinDetailUiState.Error -> {
                        it.exception.let { message ->
                            Toast.makeText(activity,"An error occured: $message", Toast.LENGTH_LONG ).show()
                        }
                    }
                    else -> Unit
                }
            }
        }
        binding.fabAddCoin.setOnClickListener {
            context?.let { it1 ->
                MaterialDialog(it1).show {
                    title(text = "Add Coin to portfolio")
                    input(hint = "Amount") { _, text ->
                        viewModel.insertCoinToPortfolio(coin,text.toString())
                    }
                    positiveButton(R.string.submit)
                    negativeButton(text = "Cancel")
                }
            }
        }
    }

    override fun onStop() {
        uiDetailStateJob?.cancel()
        super.onStop()
    }
}