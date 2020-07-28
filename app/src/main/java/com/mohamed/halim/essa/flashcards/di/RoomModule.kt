package com.mohamed.halim.essa.flashcards.di

import android.content.Context
import androidx.room.Room
import com.mohamed.halim.essa.flashcards.data.CardsDao
import com.mohamed.halim.essa.flashcards.data.CardsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {
    @Singleton
    @Provides
    fun provideCardDatabase(@ApplicationContext context: Context): CardsDatabase {
        return Room.databaseBuilder(
            context,
            CardsDatabase::class.java,
            "CardsDB"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideCardDao(database: CardsDatabase): CardsDao {
        return database.cardsDao
    }
}