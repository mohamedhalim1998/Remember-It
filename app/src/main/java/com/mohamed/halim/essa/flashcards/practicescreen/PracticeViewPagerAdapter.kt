package com.mohamed.halim.essa.flashcards.practicescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.databinding.PracticeCardBinding
import com.mohamed.halim.essa.flashcards.util.flipCard

class PracticeViewPagerAdapter(var cards: List<Card>? = null) :
    RecyclerView.Adapter<PracticeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticeViewHolder {
        return PracticeViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return cards?.size ?: 0
    }

    override fun onBindViewHolder(holder: PracticeViewHolder, position: Int) {
        holder.bind(cards!![position])
    }

    fun swapCards(cards: List<Card>?) {
        this.cards = cards
        notifyDataSetChanged()
    }
}

class PracticeViewHolder(val binding: PracticeCardBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        card: Card
    ) {
        binding.executePendingBindings()
        binding.card = card
        binding.cardView.setOnClickListener {
            (it as CardView).flipCard(card, binding.cardText)
        }
    }

    companion object {
        fun from(parent: ViewGroup): PracticeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = PracticeCardBinding.inflate(layoutInflater, parent, false)
            return PracticeViewHolder(binding)
        }
    }
}