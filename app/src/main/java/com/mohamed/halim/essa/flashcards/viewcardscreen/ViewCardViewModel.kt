package com.mohamed.halim.essa.flashcards.viewcardscreen

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mohamed.halim.essa.flashcards.data.DataSource

class ViewCardViewModel @ViewModelInject constructor(
    dataSource: DataSource,
    @Assisted private val savedStateHandle: SavedStateHandle

) : ViewModel() {
    val cardId: Long = savedStateHandle.get<Long>("cardId") ?: -1
    val card = dataSource.getCard(cardId)
}