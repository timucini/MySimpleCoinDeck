package com.example.mysimplecoindeck.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mysimplecoindeck.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoinsActivity : AppCompatActivity() {

    private val viewModel: CoinsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel
    }
}