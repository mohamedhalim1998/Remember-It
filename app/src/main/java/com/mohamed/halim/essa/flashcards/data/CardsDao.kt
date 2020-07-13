package com.mohamed.halim.essa.flashcards.data

import androidx.room.*
import com.mohamed.halim.essa.flashcards.data.model.CardSet
import io.reactivex.Single

@Dao
interface CardsDao {
    @Insert
    fun addSet(cardSet: CardSet)
    @Query("SELECT * FROM card_set")
    fun getAllSets() : Single<List<CardSet>>
    @Query("SELECT * FROM card_set WHERE :id = id")
    fun getSet(id : Long) : Single<CardSet>
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSet(cardSet: CardSet)
}