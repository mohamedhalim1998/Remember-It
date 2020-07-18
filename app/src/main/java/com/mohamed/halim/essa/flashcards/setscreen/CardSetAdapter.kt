package com.mohamed.halim.essa.flashcards.setscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.data.model.CardSet
import com.mohamed.halim.essa.flashcards.databinding.SetListItemBinding

class CardSetAdapter(
    val clickListener: CardSetClickListener,
    val cardSetOptionMenu: CardSetOptionMenu
) :
    ListAdapter<CardSet, CardSetViewHolder>(CardSetDiffCallBacks()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardSetViewHolder {
        return CardSetViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CardSetViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener, cardSetOptionMenu)
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
        ,
        clickListener: CardSetClickListener,
        cardSetOptionMenu: CardSetOptionMenu
    ) {
        binding.cardSet = cardSet
        binding.clickLister = clickListener
        binding.executePendingBindings()
        binding.optionMenu.setOnClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit_card_set -> {
                        cardSetOptionMenu.editCardSet(cardSet)
                        true
                    }
                    R.id.delete_card_set -> {
                        cardSetOptionMenu.deleteCardSet(cardSet)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.inflate(R.menu.card_set_option_menu)
            popupMenu.show()
        }
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

interface CardSetOptionMenu {
    fun editCardSet(cardSet: CardSet)
    fun deleteCardSet(cardSet: CardSet)

}
