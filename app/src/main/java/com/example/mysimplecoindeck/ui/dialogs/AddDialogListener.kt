package com.example.mysimplecoindeck.ui.dialogs

import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity

interface AddDialogListener {
    fun onAddButtonClicked(item: CoinPortfolioEntity)
}