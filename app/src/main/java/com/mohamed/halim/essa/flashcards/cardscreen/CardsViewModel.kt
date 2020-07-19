package com.mohamed.halim.essa.flashcards.cardscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamed.halim.essa.flashcards.data.DataSource

class CardsViewModel(val dataSource: DataSource, val cardSetId: Long) : ViewModel() {
    val cards = dataSource.getCardsFromSet(cardSetId)
    private val _addCardNavigation = MutableLiveData<Boolean>()
    val addCardNavigation: LiveData<Boolean>
        get() = _addCardNavigation

    fun navigateToAddCard() {
        _addCardNavigation.value = true
    }


    fun doneNavigation() {
        _addCardNavigation.value = null
    }

}