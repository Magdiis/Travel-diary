package com.example.projectandroid2.di

import com.example.projectandroid2.communication.API
import com.example.projectandroid2.communication.RemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoryModule {
    @Provides
    @Singleton
    fun provideRemoteRepository(api: API): RemoteRepositoryImpl
            = RemoteRepositoryImpl(api)

}
