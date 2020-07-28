package com.mohamed.halim.essa.flashcards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mohamed.halim.essa.flashcards.data.DataSource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var dataSource: DataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
    }

    override fun onDestroy() {
        super.onDestroy()
        dataSource.clear()
    }
}