package com.example.mysimplecoindeck.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mysimplecoindeck.databinding.PortfolioCoinPreviewBinding
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import java.math.RoundingMode

class PortfolioAdapter: RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {

    class PortfolioViewHolder(val binding: PortfolioCoinPreviewBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<CoinPortfolioEntity>() {
        override fun areItemsTheSame(oldItem: CoinPortfolioEntity, newItem: CoinPortfolioEntity): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: CoinPortfolioEntity, newItem: CoinPortfolioEntity): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        return PortfolioViewHolder(
                PortfolioCoinPreviewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        val coin = differ.currentList[position]
        holder.itemView.apply {
            with(holder.binding) {
                ivCoinLogo.load(coin.iconUrl)
                tvCoinName.text = coin.name
                tvCoinChange.text = coin.change.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toString() + "%"
                tvCoinChange.setTextColor(if (coin.change.toDouble() >= 0 ) Color.GREEN else Color.RED)
                tvCoinPrice.text = coin.price.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toString() + "$"
                tvHoldingsCoins.text = coin.amount.toBigDecimal().setScale(2,RoundingMode.HALF_UP).toString()
                tvHoldingsCurrency.text = (coin.price.toBigDecimal() * coin.amount.toBigDecimal()).setScale(2,RoundingMode.HALF_UP).toString() + "$"
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}