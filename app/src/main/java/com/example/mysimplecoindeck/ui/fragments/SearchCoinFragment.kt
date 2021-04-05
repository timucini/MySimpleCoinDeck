package com.example.mysimplecoindeck.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.adapters.SearchAdapter
import com.example.mysimplecoindeck.databinding.FragmentSearchCoinBinding
import com.example.mysimplecoindeck.ui.CoinsViewModel
import com.example.mysimplecoindeck.utils.Constants.Companion.SEARCH_QUERY_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchCoinFragment: Fragment(R.layout.fragment_search_coin) {
    private val viewModel: CoinsViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var binding: FragmentSearchCoinBinding
    private var uiSearchStateJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchCoinBinding.bind(view)
        setupRecyclerView()

        binding.etSeachText.addTextChangedListener { editable ->
            if (editable.toString().isNotEmpty()) {
                viewModel.getSuggestedCoins(editable.toString())
            }
        }

        uiSearchStateJob = lifecycleScope.launchWhenStarted {
            delay(SEARCH_QUERY_TIME_DELAY)
            viewModel.uiSearchState.collect {
                when(it) {
                    is CoinsViewModel.SearchUiState.Success -> {
                        it.coinSuggestions.let { coinsResponse ->
                            searchAdapter.differ.submitList(coinsResponse.body()?.data?.coins)
                        }
                    }
                    is CoinsViewModel.SearchUiState.Error -> {
                        it.exception.let { message ->
                            Toast.makeText(activity,"An error occured: $message", Toast.LENGTH_LONG ).show()
                        }
                    }
                    else -> Unit
                }
            }
        }
        searchAdapter.setOnClickListener {
            findNavController().navigate(
                SearchCoinFragmentDirections.actionSearchCoinFragmentToSingleCoinFragment(it)
            )

        }
    }

    override fun onStop() {
        uiSearchStateJob?.cancel()
        super.onStop()
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter()
        binding.rvSearch.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}