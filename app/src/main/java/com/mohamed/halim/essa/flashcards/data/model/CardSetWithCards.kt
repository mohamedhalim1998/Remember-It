package com.mohamed.halim.essa.flashcards.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class CardSetWithCards(
    @Embedded val cardSet: CardSet,
    @Relation(
        parentColumn = "id",
        entityColumn = "cardSetId"
    )
    val cards: List<Card>
)