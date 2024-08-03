package com.example.projectandroid2.di

import com.example.projectandroid2.communication.IRemoteRepository
import com.example.projectandroid2.fake.FakePLacesLocalRepository


import com.example.projectandroid2.placedatabase.IPlacesLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class FakeRepositoryModule {
    @Binds
    abstract fun provideLocalPlacesRepository(service: FakePLacesLocalRepository): IPlacesLocalRepository
}