package com.example.mysimplecoindeck.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.adapters.PortfolioAdapter
import com.example.mysimplecoindeck.databinding.FragmentPortfolioBinding
import com.example.mysimplecoindeck.ui.CoinsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PortfolioFragment: Fragment(R.layout.fragment_portfolio) {
    private val viewModel: CoinsViewModel by viewModels()
    private lateinit var binding: FragmentPortfolioBinding
    private lateinit var portfolioAdapter: PortfolioAdapter
    private var uiPortfolioStateJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPortfolioBinding.bind(view)
        setupRecyclerView()
        viewModel.getPortfolio()
        uiPortfolioStateJob = lifecycleScope.launchWhenStarted {
            viewModel.uiPortfolioState.collect {
                when(it) {
                    is CoinsViewModel.PortfolioUiState.Success -> {
                        it.portfolio.let { coinsPortfolioResponse ->
                            portfolioAdapter.differ.submitList(coinsPortfolioResponse)
                        }
                    }
                    is CoinsViewModel.PortfolioUiState.Error -> {
                        it.exception.let { message ->
                            Toast.makeText(activity,"An error occured: $message", Toast.LENGTH_LONG ).show()
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onStop() {
        uiPortfolioStateJob?.cancel()
        super.onStop()
    }

    private fun setupRecyclerView() {
        portfolioAdapter = PortfolioAdapter()
        binding.rvPortfolioList.apply {
            adapter = portfolioAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}