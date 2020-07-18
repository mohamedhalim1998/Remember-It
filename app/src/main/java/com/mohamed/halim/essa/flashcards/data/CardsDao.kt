package com.mohamed.halim.essa.flashcards.data

import androidx.room.*
import com.mohamed.halim.essa.flashcards.data.model.CardSet
import io.reactivex.Flowable

@Dao
interface CardsDao {
    @Insert
    fun addSet(cardSet: CardSet)

    @Query("SELECT * FROM card_set")
    fun getAllSets(): Flowable<List<CardSet>>

    @Query("SELECT * FROM card_set WHERE :id = id")
    fun getSet(id: Long): Flowable<CardSet>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSet(cardSet: CardSet)

    @Delete
    fun deleteSet(cardSet: CardSet)
}