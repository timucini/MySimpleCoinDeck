package com.example.mysimplecoindeck.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.databinding.ActivityMainBinding
import com.example.mysimplecoindeck.databinding.CoinRankingPreviewBinding
import com.example.mysimplecoindeck.models.Coin
import com.squareup.picasso.Picasso

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
            holder.binding.tvCoinPrice.text = it.price
            holder.binding.tvCoinMarketCap.text = it.marketCap.toString()
            holder.binding.tvSymbol.text = it.symbol
            Picasso.with(holder.itemView.context)
                .load(Uri.parse(it.iconUrl))
                .resize(25,25)
                .centerCrop()
                .into(holder.binding.ivIcon)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}