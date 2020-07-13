package com.mohamed.halim.essa.flashcards.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mohamed.halim.essa.flashcards.data.model.Card

class RoomConverters {
    @TypeConverter
    fun listToJson(value: List<Card>): String {
        return Gson().toJson(value).toString()
    }

    @TypeConverter
    fun jsonToList(value: String): List<Card> {
        return Gson().fromJson(value, Array<Card>::class.java).toList()

    }
}