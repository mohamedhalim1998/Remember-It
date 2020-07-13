package com.mohamed.halim.essa.flashcards.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohamed.halim.essa.flashcards.data.model.CardSet
import com.mohamed.halim.essa.flashcards.util.RoomConverters

@Database(entities = [CardSet::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class CardsDatabase() : RoomDatabase() {
    abstract var cardsDao: CardsDao

    companion object {
        @Volatile
        var INSTANCE: CardsDatabase? = null

        fun getInstance(context: Context): CardsDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    CardsDatabase::class.java,
                    "CardsDB"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
            }
            return instance
        }
    }
}