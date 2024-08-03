package com.example.projectandroid2.di

import com.example.projectandroid2.firebase.FirestoreDB
import com.example.projectandroid2.firebase.FirestoreDBRepositoryImpl
import com.example.projectandroid2.firebase.IFirestoreDBRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {
    @Singleton
    @Provides
    fun provideFirestoreDBReference(): CollectionReference = Firebase.firestore.collection("countries")

    @Singleton
    @Provides
    fun provideFirestoreDBRepository(firestoreDB: FirestoreDB): IFirestoreDBRepository =
        FirestoreDBRepositoryImpl(firestoreDB)
}