package com.mohamed.halim.essa.flashcards.setscreen

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mohamed.halim.essa.flashcards.data.DataSource
import com.mohamed.halim.essa.flashcards.data.model.CardSet

class SetViewModel @ViewModelInject constructor(
    val dataSource: DataSource,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val cardSets = dataSource.getAllSets()

    private val _addSetDialog = MutableLiveData<Boolean>()

    val addSetDialog: LiveData<Boolean>
        get() = _addSetDialog

    fun showAddSetDialog() {
        _addSetDialog.value = true
    }

    fun hideAddSetDialog() {
        _addSetDialog.value = null
    }

    fun addCardSet(name: String) {
        dataSource.addCardSet(CardSet(name = name))
    }

    fun updateCardSet(cardSet: CardSet) {
        dataSource.updateCardSet(cardSet)
    }

    fun deleteCardSet(cardSet: CardSet) {
        dataSource.deleteCardSet(cardSet)
    }

}