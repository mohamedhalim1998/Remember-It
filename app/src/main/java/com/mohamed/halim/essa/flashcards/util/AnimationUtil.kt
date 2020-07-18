package com.mohamed.halim.essa.flashcards.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.mohamed.halim.essa.flashcards.data.model.Card

fun CardView.flipCard(card: Card, cardText: TextView) {
    ObjectAnimator.ofFloat(this, View.ROTATION_Y, -180f, 0f).apply {
        repeatMode = ObjectAnimator.REVERSE
        cardText.visibility = View.INVISIBLE
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                cardText.changeText(card)
                cardText.visibility = View.VISIBLE
            }
        })
        start()
    }

}

fun TextView.changeText(card: Card) {
    text = if (text == card.firstSide) {
        card.secondSide
    } else {
        card.firstSide
    }

}