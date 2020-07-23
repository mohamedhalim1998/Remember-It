package com.mohamed.halim.essa.flashcards.addcardscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohamed.halim.essa.flashcards.data.DataSource

class AddCardViewModelFactory(val application: Application, val dataSource: DataSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCardViewModel::class.java)) {
            return AddCardViewModel(application, dataSource) as T
        }
        throw IllegalArgumentException()
    }

}