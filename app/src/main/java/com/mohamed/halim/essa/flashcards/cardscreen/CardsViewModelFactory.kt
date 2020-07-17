package com.mohamed.halim.essa.flashcards.cardscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohamed.halim.essa.flashcards.data.DataSource
import com.mohamed.halim.essa.flashcards.setscreen.SetViewModel

class CardsViewModelFactory(val dataSource: DataSource, val id: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardsViewModel::class.java)) {
            return CardsViewModel(dataSource, id) as T
        }
        throw IllegalArgumentException()
    }



}