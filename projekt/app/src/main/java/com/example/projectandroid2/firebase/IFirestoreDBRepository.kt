package com.example.projectandroid2.firebase

import com.example.projectandroid2.model.Country
import kotlinx.coroutines.flow.Flow


interface IFirestoreDBRepository {
    fun getCountries(): Flow<FirestoreDBResult<List<Country?>>>
}