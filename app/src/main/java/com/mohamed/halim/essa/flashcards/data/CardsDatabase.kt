package com.mohamed.halim.essa.flashcards.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.data.model.CardSet

@Database(entities = [CardSet::class, Card::class], version = 4)
abstract class CardsDatabase() : RoomDatabase() {
    abstract val cardsDao: CardsDao
}