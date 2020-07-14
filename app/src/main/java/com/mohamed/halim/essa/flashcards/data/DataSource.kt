package com.mohamed.halim.essa.flashcards.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.mohamed.halim.essa.flashcards.data.model.CardSet
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataSource(val database: CardsDao) {
    fun getAllSets(): LiveData<List<CardSet>> {
        return LiveDataReactiveStreams.fromPublisher(
            database.getAllSets().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        )
    }

    fun addCardSet(cardSet : CardSet) {
        Observable.fromCallable {
            database.addSet(cardSet)
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}