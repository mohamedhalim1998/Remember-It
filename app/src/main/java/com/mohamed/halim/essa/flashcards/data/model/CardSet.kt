package com.mohamed.halim.essa.flashcards.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohamed.halim.essa.flashcards.data.model.Card

@Entity(tableName = "card_set")
data class CardSet(@PrimaryKey var id: Long? = null, val name: String, var cards: List<Card>)