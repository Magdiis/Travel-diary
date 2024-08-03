package com.example.projectandroid2.di

import com.example.projectandroid2.fake.FakeFirestoreDBRepositoryImpl
import com.example.projectandroid2.firebase.IFirestoreDBRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FirestoreModule::class],
)
abstract class FakeFirestoreModule {
//    @Binds
//    abstract fun provideFirestoreDBReference()

    @Binds
    abstract fun provideFirestoreDBRepository(service:FakeFirestoreDBRepositoryImpl): IFirestoreDBRepository

}