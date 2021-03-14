package com.example.mysimplecoindeck.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.adapters.CoinsAdapter
import com.example.mysimplecoindeck.databinding.FragmentCoinsRankingBinding
import com.example.mysimplecoindeck.ui.CoinsViewModel
import com.example.mysimplecoindeck.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinsRankingFragment : Fragment(R.layout.fragment_coins_ranking) {

    private val viewModel: CoinsViewModel by viewModels()
    private lateinit var coinsAdapter: CoinsAdapter
    private lateinit var binding: FragmentCoinsRankingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCoinsRankingBinding.bind(view)
        setupRecyclerView()

        viewModel.coinsList.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { coinsListResponse ->
                        coinsAdapter.differ.submitList(coinsListResponse.body()?.data?.coins?.sortedByDescending { it.marketCap })
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        coinsAdapter = CoinsAdapter()
        binding.rvCoinsRanking.apply {
            adapter = coinsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}