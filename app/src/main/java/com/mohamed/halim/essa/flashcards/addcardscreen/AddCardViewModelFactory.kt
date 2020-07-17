package com.mohamed.halim.essa.flashcards.addcardscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohamed.halim.essa.flashcards.data.DataSource

class AddCardViewModelFactory(val dataSource: DataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCardViewModel::class.java)) {
            return AddCardViewModel(dataSource) as T
        }
        throw IllegalArgumentException()
    }

}