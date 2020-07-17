package com.mohamed.halim.essa.flashcards.data

import android.content.Context
import androidx.lifecycle.*
import androidx.room.Room
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.data.model.CardSet
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class DataSource private constructor(val database: CardsDao) {
    private val cardSet = MediatorLiveData<CardSet>()
    private val disposables = CompositeDisposable()

    companion object {
        @Volatile
        private var INSTANCE: DataSource? = null

        fun getInstance(database: CardsDao): DataSource {
            var instance = INSTANCE
            if (instance == null) {
                instance = DataSource(database)
                INSTANCE = instance
            }
            return instance
        }
    }


    fun getAllSets(): LiveData<List<CardSet>> {
        return LiveDataReactiveStreams.fromPublisher(
            database.getAllSets().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        )
    }

    fun addCardSet(cardSet: CardSet) {
        disposables.add(
            Observable.fromCallable {
                database.addSet(cardSet)
            }.subscribeOn(Schedulers.io()).subscribe()
        )
    }

    fun getCardSet(id: Long): LiveData<CardSet> {
        val source = LiveDataReactiveStreams.fromPublisher(
            database.getSet(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        )
        cardSet.addSource(source) {
            cardSet.value = it
        }
        return cardSet
    }

    fun addCard(card: Card) {
        val set = cardSet.value
        if (set != null) {
            set.cards.add(card)
            disposables.add(
                Observable.fromCallable {
                    database.updateSet(
                        set
                    )
                }.subscribeOn(Schedulers.io()).subscribe()
            )
        }
    }

    fun clear() {
        disposables.clear()
    }
}