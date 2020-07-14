package com.mohamed.halim.essa.flashcards.util

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("numberText")
fun numberText(textView: TextView, num : Int) {
    textView.text  = num.toString()
}