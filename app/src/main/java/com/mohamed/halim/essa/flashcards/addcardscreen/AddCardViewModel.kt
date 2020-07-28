package com.mohamed.halim.essa.flashcards.addcardscreen

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mohamed.halim.essa.flashcards.R
import com.mohamed.halim.essa.flashcards.data.DataSource
import com.mohamed.halim.essa.flashcards.data.model.Card

class AddCardViewModel
@ViewModelInject constructor(
    val app: Application, val dataSource: DataSource,
    @Assisted private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(app) {

    private val _cardColor = MutableLiveData<Int>()
    val cardColor: LiveData<Int>
        get() = _cardColor

    init {
        _cardColor.value = ContextCompat.getColor(app.applicationContext, R.color.card_white)
    }

    fun addCard(card: Card) {
        dataSource.addCard(card)
    }

    fun updateCard(card: Card) {
        dataSource.updateCard(card)
    }

    fun getCard(cardId: Long): LiveData<Card> {
        return dataSource.getCard(cardId)
    }

    fun changeColorToYellow() {
        _cardColor.value = ContextCompat.getColor(app.applicationContext, R.color.card_yellow)
    }

    fun changeColorToBlue() {
        _cardColor.value = ContextCompat.getColor(app.applicationContext, R.color.card_blue)
    }

    fun changeColorToWhite() {
        _cardColor.value = ContextCompat.getColor(app.applicationContext, R.color.card_white)
    }

    fun changeColorToOrange() {
        _cardColor.value = ContextCompat.getColor(app.applicationContext, R.color.card_orange)
    }

    fun changeColorToRed() {
        _cardColor.value = ContextCompat.getColor(app.applicationContext, R.color.card_red)
    }

    fun changeColorToGreen() {
        _cardColor.value = ContextCompat.getColor(app.applicationContext, R.color.card_green)
    }
}