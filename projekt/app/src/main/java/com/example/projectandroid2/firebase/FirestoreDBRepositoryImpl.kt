package com.example.projectandroid2.firebase

import com.example.projectandroid2.model.Country
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreDBRepositoryImpl @Inject constructor(private val firestoreDB: FirestoreDB): IFirestoreDBRepository {
    override fun getCountries(): Flow<FirestoreDBResult<List<Country?>>> = firestoreDB.getCountries()

}