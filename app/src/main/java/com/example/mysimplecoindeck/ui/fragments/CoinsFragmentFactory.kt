package com.example.mysimplecoindeck.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.mysimplecoindeck.adapters.CoinsAdapter
import com.example.mysimplecoindeck.adapters.PortfolioAdapter
import com.example.mysimplecoindeck.adapters.SearchAdapter
import javax.inject.Inject

class CoinsFragmentFactory @Inject constructor(
        private val coinsAdapter: CoinsAdapter,
        private val searchAdapter: SearchAdapter,
        private val portfolioAdapter: PortfolioAdapter
): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            CoinsRankingFragment::class.java.name -> CoinsRankingFragment(coinsAdapter)
            SearchCoinFragment::class.java.name -> SearchCoinFragment(searchAdapter)
            PortfolioFragment::class.java.name -> PortfolioFragment(portfolioAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}