package com.example.projectandroid2.di

import com.example.projectandroid2.communication.API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): API =
        retrofit.create(API::class.java)
}