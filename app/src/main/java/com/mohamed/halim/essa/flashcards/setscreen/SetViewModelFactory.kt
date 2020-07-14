package com.mohamed.halim.essa.flashcards.setscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohamed.halim.essa.flashcards.data.DataSource

class SetViewModelFactory(val dataSource: DataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetViewModel::class.java)) {
            return SetViewModel(dataSource) as T
        }
        throw IllegalArgumentException()
    }

}