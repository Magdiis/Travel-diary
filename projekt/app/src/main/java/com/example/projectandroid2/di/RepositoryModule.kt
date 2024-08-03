package com.example.projectandroid2.di

import com.example.projectandroid2.placedatabase.IPlacesLocalRepository
import com.example.projectandroid2.placedatabase.PlacesDao
import com.example.projectandroid2.placedatabase.PlacesLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object RepositoryModule {
    @Provides
    @Singleton
    fun provideLocalPlacesRepository(dao: PlacesDao): IPlacesLocalRepository {
        return PlacesLocalRepositoryImpl(dao)
    }


}