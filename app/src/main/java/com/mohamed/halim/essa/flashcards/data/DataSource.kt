package com.mohamed.halim.essa.flashcards.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.data.model.CardSet
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DataSource private constructor(private val database: CardsDao) {
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

    //    fun getCardSet(id: Long): LiveData<CardSet> {
//        val source = LiveDataReactiveStreams.fromPublisher(
//            database.getSet(id).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//        )
//        cardSet.addSource(source) {
//            cardSet.value = it
//        }
//        return cardSet
//    }
    fun getCardsFromSet(id: Long): LiveData<List<Card>> {
        return LiveDataReactiveStreams.fromPublisher(
            database.getAllCardsInSet(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        )
    }

    fun addCard(card: Card) {
        disposables.add(
            Observable.fromCallable {
                database.addCard(card)
            }.subscribeOn(Schedulers.io()).subscribe()
        )

    }

    fun updateCardSet(cardSet: CardSet) {
        disposables.add(
            Observable.fromCallable {
                database.updateSet(cardSet)
            }.subscribeOn(Schedulers.io()).subscribe()
        )
        getAllSets()
    }

    fun deleteCardSet(cardSet: CardSet) {
        disposables.add(
            Observable.fromCallable {
                database.deleteSet(cardSet)
            }.subscribeOn(Schedulers.io()).subscribe()
        )
        getAllSets()

    }

    fun clear() {
        disposables.clear()
    }


}