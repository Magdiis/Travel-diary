package com.example.projectandroid2.di

import android.content.Context
import androidx.room.Room
import com.example.projectandroid2.ProjectApplication
import com.example.projectandroid2.placedatabase.PlacesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(): PlacesDatabase = PlacesDatabase.getDatabase(ProjectApplication.appContext)
}
