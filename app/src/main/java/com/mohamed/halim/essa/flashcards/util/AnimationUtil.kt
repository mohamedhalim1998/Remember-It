package com.mohamed.halim.essa.flashcards.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.util.Direction.BACKWAED
import com.mohamed.halim.essa.flashcards.util.Direction.FORWARD

fun CardView.flipCard(card: Card, cardText: TextView, optionMenIcon: ImageView? = null) {

    val animator = if (cardText.text == card.firstSide) setUpObjectAnimator(this, FORWARD)
    else setUpObjectAnimator(this, BACKWAED)
    animator.apply {
        repeatMode = ObjectAnimator.REVERSE
        cardText.visibility = View.INVISIBLE
        optionMenIcon?.visibility = View.INVISIBLE
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                cardText.changeText(card)
                cardText.visibility = View.VISIBLE
                optionMenIcon?.visibility = View.VISIBLE
            }
        })
        start()
    }

}

fun setUpObjectAnimator(view: View, direction: Direction): ObjectAnimator {
    return when (direction) {
        FORWARD -> ObjectAnimator.ofFloat(view, View.ROTATION_Y, -180f, 0f)
        BACKWAED -> ObjectAnimator.ofFloat(view, View.ROTATION_Y, 180f, 0f)
    }
}

fun TextView.changeText(card: Card) {
    text = if (text == card.firstSide) {
        card.secondSide
    } else {
        card.firstSide
    }

}

enum class Direction {
    FORWARD, BACKWAED
}