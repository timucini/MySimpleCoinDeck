package com.example.mysimplecoindeck.ui.fragments

import android.content.Context
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.navigation.fragment.NavHostFragment

@AndroidEntryPoint
class NavHostFragment: NavHostFragment() {

    @Inject
    lateinit var fragmentFactory: CoinsFragmentFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        childFragmentManager.fragmentFactory = fragmentFactory
    }
}