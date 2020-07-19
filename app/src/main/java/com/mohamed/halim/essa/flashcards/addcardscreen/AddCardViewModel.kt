package com.mohamed.halim.essa.flashcards.addcardscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohamed.halim.essa.flashcards.data.DataSource
import com.mohamed.halim.essa.flashcards.data.model.Card

class AddCardViewModel(val dataSource: DataSource) : ViewModel() {

    fun addCard(card: Card) {
        dataSource.addCard(card)
    }

    fun updateCard(card: Card) {
        dataSource.updateCard(card)
    }

    fun getCard(cardId: Long): LiveData<Card> {
        return dataSource.getCard(cardId)
    }

}