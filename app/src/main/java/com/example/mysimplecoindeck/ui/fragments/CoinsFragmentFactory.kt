package com.example.mysimplecoindeck.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.mysimplecoindeck.adapters.CoinsAdapter
import com.example.mysimplecoindeck.adapters.PortfolioAdapter
import com.example.mysimplecoindeck.adapters.SearchAdapter
import com.example.mysimplecoindeck.ui.CoinsViewModel
import javax.inject.Inject

class CoinsFragmentFactory @Inject constructor(
        private val coinsAdapter: CoinsAdapter,
        private val searchAdapter: SearchAdapter,
        private val portfolioAdapter: PortfolioAdapter,
        private val coinsViewModel: CoinsViewModel
): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            CoinsRankingFragment::class.java.name -> CoinsRankingFragment(coinsAdapter,coinsViewModel)
            SearchCoinFragment::class.java.name -> SearchCoinFragment(searchAdapter,coinsViewModel)
            PortfolioFragment::class.java.name -> PortfolioFragment(portfolioAdapter,coinsViewModel)
            SingleCoinFragment::class.java.name -> SingleCoinFragment(coinsViewModel)
            else -> super.instantiate(classLoader, className)
        }
    }
}