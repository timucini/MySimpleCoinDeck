package com.example.mysimplecoindeck.ui.dialogs

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.databinding.DialogAddCoinBinding
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import com.example.mysimplecoindeck.models.singleCoin.Coin

class AddCoinDialog(context: Context?, var addDialogListener: AddDialogListener, var coin: Coin): AppCompatDialog(context) {
    private lateinit var binding: DialogAddCoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddCoinBinding.inflate(layoutInflater)
        setContentView(R.layout.dialog_add_coin)
        val amount = binding.etAmountCoin.text.toString()
        val insertEntity = CoinPortfolioEntity(
                coin.uuid,
                coin.name,
                coin.iconUrl,
                coin.price,
                coin.change,
                amount
        )
        binding.btnAdd.setOnClickListener {
            addDialogListener.onAddButtonClicked(insertEntity)
        }
        binding.btnCancel.setOnClickListener {
            cancel()
        }
    }
}