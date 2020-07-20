package com.mohamed.halim.essa.flashcards.cardscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.databinding.CardListItemBinding
import com.mohamed.halim.essa.flashcards.util.flipCard

class CardAdapter(private val cardOptionMenu: CardOptionMenu) :
    ListAdapter<Card, CardViewHolder>(CardDiffCallBacks()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position), cardOptionMenu)
    }

}

class CardDiffCallBacks : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.cardId == newItem.cardId
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }

}

class CardViewHolder private constructor(val binding: CardListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        card: Card,
        cardOptionMenu: CardOptionMenu
    ) {
        binding.executePendingBindings()
        binding.card = card
        binding.cardView.setOnClickListener {
            (it as CardView).flipCard(card, binding.cardText, binding.optionMenu)
        }
        binding.optionMenu.setOnClickListener { view ->
            val menuItemClickListener = createMenuItemClickListener(cardOptionMenu, card)
            inflateOptionMenu(view, menuItemClickListener)
        }

    }

    private fun inflateOptionMenu(
        view: View,
        menuItemClickListener: PopupMenu.OnMenuItemClickListener
    ) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.setOnMenuItemClickListener(menuItemClickListener)
        popupMenu.inflate(R.menu.card_option_menu)
        popupMenu.show()
    }

    private fun createMenuItemClickListener(
        cardOptionMenu: CardOptionMenu,
        card: Card
    ): PopupMenu.OnMenuItemClickListener {
        return PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.edit_card -> {
                    cardOptionMenu.editCard(card.cardId!!)
                    true
                }
                R.id.delete_card -> {
                    cardOptionMenu.deleteCard(card.cardId!!)
                    true
                }
                R.id.view_card -> {
                    cardOptionMenu.viewCard(card.cardId!!)
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): CardViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CardListItemBinding.inflate(layoutInflater, parent, false)
            return CardViewHolder(binding)
        }
    }

}

interface CardOptionMenu {
    fun editCard(cardId: Long)
    fun deleteCard(cardId: Long)
    fun viewCard(cardId: Long)
}
