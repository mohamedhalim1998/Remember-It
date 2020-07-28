package com.mohamed.halim.essa.flashcards

import android.app.Application
import com.mohamed.halim.essa.flashcards.util.createNotificationChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlashCardsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this)
    }

}