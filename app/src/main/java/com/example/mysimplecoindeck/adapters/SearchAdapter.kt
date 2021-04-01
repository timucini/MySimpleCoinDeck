package com.example.mysimplecoindeck.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mysimplecoindeck.databinding.SearchCoinPreviewBinding
import com.example.mysimplecoindeck.models.searchSuggestions.Coin

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(val binding: SearchCoinPreviewBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            SearchCoinPreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val coin = differ.currentList[position]
        holder.itemView.apply {
            with(holder.binding) {
                tvSearchCoinName.text = coin.name
                ivSearchIcon.load(coin.iconUrl)
            }
            setOnClickListener {
                onItemClickListener?.let { it(coin.uuid) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}