package com.mohamed.halim.essa.flashcards.viewcardscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohamed.halim.essa.flashcards.data.DataSource

class ViewCardViewModelFactory(val dataSource: DataSource, val cardId: Long) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewCardViewModel::class.java)) {
            return ViewCardViewModel(dataSource, cardId) as T
        }
        throw IllegalArgumentException()
    }

}