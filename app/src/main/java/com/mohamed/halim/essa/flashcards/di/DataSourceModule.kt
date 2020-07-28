package com.mohamed.halim.essa.flashcards.di

import com.mohamed.halim.essa.flashcards.data.CardsDao
import com.mohamed.halim.essa.flashcards.data.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DataSourceModule {
    @Singleton
    @Provides
    fun provideDataSource(
        database: CardsDao
    ): DataSource {
        return DataSource(database)
    }
}