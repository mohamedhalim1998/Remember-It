package com.mohamed.halim.essa.flashcards

import android.app.Application
import com.mohamed.halim.essa.flashcards.util.createNotificationChannel

class FlashCardsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this)
    }
}