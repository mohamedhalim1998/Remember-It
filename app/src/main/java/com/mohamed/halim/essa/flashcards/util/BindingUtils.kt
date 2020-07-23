package com.mohamed.halim.essa.flashcards.util

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.mohamed.halim.essa.flashcards.R

@BindingAdapter("numberText")
fun numberText(textView: TextView, num: Int) {
    textView.text = num.toString()
}

@BindingAdapter(value = ["score", "maxScore"])
fun scoreText(textView: TextView, num: Int, max: Int) {
    textView.apply {
        text = context.getString(R.string.score_text, num, max)
    }
}

@BindingAdapter("customCardBackground")
fun customCardBackground(cardView: CardView, color: Int) {
    cardView.setCardBackgroundColor(color)
}