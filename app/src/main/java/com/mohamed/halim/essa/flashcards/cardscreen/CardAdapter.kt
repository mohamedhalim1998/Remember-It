package com.mohamed.halim.essa.flashcards.cardscreen

import android.animation.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.databinding.CardListItemBinding

class CardAdapter() :
    ListAdapter<Card, CardViewHolder>(CardDiffCallBacks()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class CardDiffCallBacks : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }

}

class CardViewHolder private constructor(val binding: CardListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        card: Card
    ) {
        binding.executePendingBindings()
        binding.card = card
        binding.root.setOnClickListener {
            animate(card, it, binding.cardText)
        }
    }

    private fun animate(card: Card, view: View, cardText: TextView) {
        ObjectAnimator.ofFloat(view, View.ROTATION_Y, -180f, 0f).apply {
            repeatMode = ObjectAnimator.REVERSE
            cardText.visibility = View.INVISIBLE
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    changeText(card)
                    cardText.visibility = View.VISIBLE
                }
            })
            start()
        }

    }

    private fun changeText(card: Card) {
        binding.cardText.apply {
            text = if (text == card.firstSide) {
                card.secondSide
            } else {
                card.firstSide
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
