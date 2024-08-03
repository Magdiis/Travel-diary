package com.example.projectandroid2.di

import com.example.projectandroid2.communication.IRemoteRepository
import com.example.projectandroid2.fake.FakeRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RemoteRepositoryModule::class]
)
abstract class FakeRemoteRepositoryModule {
    @Binds
    abstract fun provideRemoteRepository(service: FakeRemoteRepositoryImpl): IRemoteRepository
}