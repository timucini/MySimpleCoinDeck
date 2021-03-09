package com.example.mysimplecoindeck.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mysimplecoindeck.R


class CoinsActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(CoinsViewModel::class.java)
    }
}