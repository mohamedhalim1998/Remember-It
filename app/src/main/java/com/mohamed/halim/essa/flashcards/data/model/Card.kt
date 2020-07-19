package com.mohamed.halim.essa.flashcards.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = CardSet::class,
        parentColumns = ["id"], childColumns = ["cardSetId"],
        onDelete = CASCADE
    )], tableName = "card"
)
data class Card(
    var firstSide: String,
    var secondSide: String,
    val cardSetId: Long,
    @PrimaryKey(autoGenerate = true)
    var cardId: Long? = null

)