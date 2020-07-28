package com.mohamed.halim.essa.flashcards.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.data.model.CardSet
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DataSource constructor(private val database: CardsDao) {
    private val disposables = CompositeDisposable()

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
        return LiveDataReactiveStreams.fromPublisher(
            database.getSet(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        )
    }

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

    fun getCard(cardId: Long): LiveData<Card> {
        return LiveDataReactiveStreams.fromPublisher(
            database.getCard(cardId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        )
    }

    fun deleteCard(cardId: Long) {
        disposables.add(
            Observable.fromCallable {
                database.deleteCard(cardId)
            }.subscribeOn(Schedulers.io()).subscribe()
        )
    }

    fun updateCard(card: Card) {
        disposables.add(
            Observable.fromCallable {
                database.updateCard(card)
            }.subscribeOn(Schedulers.io()).subscribe()
        )
    }

    fun clear() {
        disposables.clear()
    }
}