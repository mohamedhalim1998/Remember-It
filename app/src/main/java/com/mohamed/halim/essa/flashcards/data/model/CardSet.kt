package com.mohamed.halim.essa.flashcards.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_set")
data class CardSet(@PrimaryKey var id: Long? = null, var name: String, var cards: MutableList<Card>)