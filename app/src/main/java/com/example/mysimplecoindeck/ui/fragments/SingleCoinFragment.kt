package com.example.mysimplecoindeck.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.databinding.FragmentCoinDetailBinding
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import com.example.mysimplecoindeck.models.singleCoin.Coin
import com.example.mysimplecoindeck.ui.CoinsViewModel
import com.example.mysimplecoindeck.ui.dialogs.AddCoinDialog
import com.example.mysimplecoindeck.ui.dialogs.AddDialogListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.math.RoundingMode

@AndroidEntryPoint
class SingleCoinFragment: Fragment(R.layout.fragment_coin_detail) {
    private val viewModel: CoinsViewModel by viewModels()
    private lateinit var binding: FragmentCoinDetailBinding
    private val args: SingleCoinFragmentArgs by navArgs()
    private lateinit var coin: Coin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCoinDetailBinding.bind(view)
        viewModel.getCoinDetails(args.Uuid)
        lifecycleScope.launchWhenStarted {
            viewModel.uiCoinDetailState.collect {
                when(it) {
                    is CoinsViewModel.CoinDetailUiState.Success -> {
                        it.coins.let { coinsDetailResponse ->
                             coinsDetailResponse.body()?.data?.coin.let {
                                 coinResponse ->
                                 if (coinResponse != null) {
                                     coin = coinResponse
                                 }
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
                        it.exception?.let { message ->
                            Toast.makeText(activity,"An error occured: $message", Toast.LENGTH_LONG ).show()
                        }
                    }
                    else -> Unit
                }
            }
        }
        binding.fabAddCoin.setOnClickListener {
            AddCoinDialog(context,
                        object: AddDialogListener {
                            override fun onAddButtonClicked(item: CoinPortfolioEntity) {
                                viewModel.insertCoinToPortfolio(item)
                            }
                        },coin).show()
            }
    }
}