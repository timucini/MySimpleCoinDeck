package com.example.mysimplecoindeck.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoinsActivity : AppCompatActivity() {

    private val viewModel: CoinsViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.newNavHostFragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.SingleCoinFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
        bottomNavigationView.setupWithNavController(navController)
    }
    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }
    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE
    }
}
