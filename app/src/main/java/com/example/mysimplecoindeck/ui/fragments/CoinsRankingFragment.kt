package com.example.mysimplecoindeck.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.adapters.CoinsAdapter
import com.example.mysimplecoindeck.databinding.FragmentCoinsRankingBinding
import com.example.mysimplecoindeck.ui.CoinsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CoinsRankingFragment : Fragment(R.layout.fragment_coins_ranking) {

    private val viewModel: CoinsViewModel by viewModels()
    private lateinit var coinsAdapter: CoinsAdapter
    private lateinit var binding: FragmentCoinsRankingBinding
    private var uiStateJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCoinsRankingBinding.bind(view)
        setupRecyclerView()

        uiStateJob = lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when(it) {
                    is CoinsViewModel.CoinsListUiState.Success -> {
                        it.coins.let { coinsResponse ->
                            coinsAdapter.differ.submitList(coinsResponse.body()?.data?.coins?.sortedBy { coin -> coin.rank })
                        }
                    }
                    is CoinsViewModel.CoinsListUiState.Error -> {
                        it.exception.let { message ->
                            Toast.makeText(activity,"An error occured: $message", Toast.LENGTH_LONG ).show()
                        }
                    }
                    else -> Unit
                }
            }
        }
        coinsAdapter.setOnClickListener {
            findNavController().navigate(
                    CoinsRankingFragmentDirections.actionCoinRankingFragmentToSingleCoinFragment(it)
            )
        }
    }

    override fun onStop() {
        uiStateJob?.cancel()
        super.onStop()
    }

    private fun setupRecyclerView() {
        coinsAdapter = CoinsAdapter()
        binding.rvCoinsRanking.apply {
            adapter = coinsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}