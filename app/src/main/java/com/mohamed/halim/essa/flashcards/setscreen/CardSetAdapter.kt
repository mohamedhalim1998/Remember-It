package com.mohamed.halim.essa.flashcards.setscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mohamed.halim.essa.flashcards.data.model.CardSet
import com.mohamed.halim.essa.flashcards.databinding.SetListItemBinding

class CardSetAdapter(val clickListener: CardSetClickListener) :
    ListAdapter<CardSet, CardSetViewHolder>(CardSetDiffCallBacks()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardSetViewHolder {
        return CardSetViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CardSetViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

}

class CardSetDiffCallBacks : DiffUtil.ItemCallback<CardSet>() {
    override fun areItemsTheSame(oldItem: CardSet, newItem: CardSet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardSet, newItem: CardSet): Boolean {
        return oldItem == newItem
    }

}

class CardSetViewHolder private constructor(val binding: SetListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cardSet: CardSet
    , clickListener: CardSetClickListener
    ) {
        binding.cardSet = cardSet
        binding.clickLister = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): CardSetViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SetListItemBinding.inflate(layoutInflater, parent, false)
            return CardSetViewHolder(binding)
        }
    }

}

class CardSetClickListener(val clickListener: (id: Long) -> Unit) {
    fun onClick(cardSet: CardSet) {
        clickListener(cardSet.id!!)
    }
}
