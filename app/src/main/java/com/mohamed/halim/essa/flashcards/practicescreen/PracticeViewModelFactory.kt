package com.mohamed.halim.essa.flashcards.practicescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohamed.halim.essa.flashcards.data.DataSource

@Suppress("UNCHECKED_CAST")
class PracticeViewModelFactory(val dataSource: DataSource, val cardSetId: Long) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PracticeViewModel::class.java)) {
            return PracticeViewModel(dataSource, cardSetId) as T
        }
        throw IllegalArgumentException();
    }

}