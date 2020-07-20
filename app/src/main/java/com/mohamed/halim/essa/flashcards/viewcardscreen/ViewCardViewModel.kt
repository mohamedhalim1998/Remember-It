package com.mohamed.halim.essa.flashcards.viewcardscreen

import androidx.lifecycle.ViewModel
import com.mohamed.halim.essa.flashcards.data.DataSource

class ViewCardViewModel(
    dataSource: DataSource,
    cardId: Long
) : ViewModel() {

    val card = dataSource.getCard(cardId)
}