package com.example.mysimplecoindeck.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mysimplecoindeck.databinding.CoinRankingPreviewBinding
import com.example.mysimplecoindeck.models.Coin
import java.math.RoundingMode

class CoinsAdapter: RecyclerView.Adapter<CoinsAdapter.CoinsViewHolder>() {

    class CoinsViewHolder(val binding: CoinRankingPreviewBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    var coins: List<Coin>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        return CoinsViewHolder(
                CoinRankingPreviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        val coin = coins[position]
        holder.itemView.apply {
            with(holder.binding) {
                tvCoinName.text = coin.name
                tvCoinPrice.text = coin.price.toBigDecimal().setScale(2,RoundingMode.HALF_UP).toString() + "$"
                tvChange.text = coin.change.toBigDecimal().setScale(2,RoundingMode.HALF_UP).toString() + "%"
                tvChange.setTextColor(if (coin.change.toDouble() >= 0 ) Color.GREEN else Color.RED)
                tvSymbol.text = coin.symbol
                Icon.load(coin.iconUrl)
            }
            setOnClickListener {
                onItemClickListener?.let { it(coin.uuid) }
            }
        }
    }

    override fun getItemCount(): Int {
        return coins.size
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}