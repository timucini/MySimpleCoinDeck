package com.example.mysimplecoindeck.adapters

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.databinding.ActivityMainBinding
import com.example.mysimplecoindeck.databinding.CoinRankingPreviewBinding
import com.example.mysimplecoindeck.models.Coin
import com.example.mysimplecoindeck.utils.GlideApp
import java.math.RoundingMode

class CoinsAdapter: RecyclerView.Adapter<CoinsAdapter.CoinsViewHolder>() {

    class CoinsViewHolder(val binding: CoinRankingPreviewBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

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
        val coin = differ.currentList[position]
        coin.let {
            holder.binding.tvCoinName.text = it.name
            holder.binding.tvCoinPrice.text = it.price.toBigDecimal().setScale(2,RoundingMode.HALF_UP).toString() + "$"
            holder.binding.tvChange.text = it.change.toString() + "%"
            holder.binding.tvChange.setTextColor(if (it.change >= 0 ) Color.GREEN else Color.RED)
            holder.binding.tvSymbol.text = it.symbol
            holder.binding.ivIcon.load(it.iconUrl)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}