package com.mohamed.halim.essa.flashcards.cardscreen

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mohamed.halim.essa.flashcards.data.DataSource
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.data.model.CardSet

class CardsViewModel
@ViewModelInject
constructor(
    val dataSource: DataSource,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var cardSetId: Long = savedStateHandle.get<Long>("cardSetId") ?: -1
    val cards: LiveData<List<Card>> by lazy {
        dataSource.getCardsFromSet(cardSetId)
    }
    val cardSet: LiveData<CardSet> by lazy {
        dataSource.getCardSet(cardSetId)
    }
    private val _addCardNavigation = MutableLiveData<Boolean>()
    val addCardNavigation: LiveData<Boolean>
        get() = _addCardNavigation

    fun navigateToAddCard() {
        _addCardNavigation.value = true
    }


    fun doneNavigation() {
        _addCardNavigation.value = null
    }

    fun deleteCard(cardId: Long) {
        dataSource.deleteCard(cardId)
    }

}