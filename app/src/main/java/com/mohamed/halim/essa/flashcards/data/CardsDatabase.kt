package com.mohamed.halim.essa.flashcards.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mohamed.halim.essa.flashcards.data.model.Card
import com.mohamed.halim.essa.flashcards.data.model.CardSet

@Database(entities = [CardSet::class, Card::class], version = 4)
abstract class CardsDatabase() : RoomDatabase() {
    abstract val cardsDao: CardsDao

    companion object {
        @Volatile
        private var INSTANCE: CardsDatabase? = null

        fun getInstance(context: Context): CardsDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardsDatabase::class.java,
                    "CardsDB"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
            }
            return instance
        }
    }
}