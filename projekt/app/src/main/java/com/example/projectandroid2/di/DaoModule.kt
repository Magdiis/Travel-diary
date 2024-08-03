package com.example.projectandroid2.di

import com.example.projectandroid2.placedatabase.PlacesDao
import com.example.projectandroid2.placedatabase.PlacesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object DaoModule {
    @Provides
    @Singleton
    fun provideDao(database: PlacesDatabase): PlacesDao {
        return database.placesDao()
    }

}