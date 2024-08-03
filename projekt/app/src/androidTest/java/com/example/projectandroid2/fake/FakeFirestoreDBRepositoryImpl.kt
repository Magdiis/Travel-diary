package com.example.projectandroid2.fake

import com.example.projectandroid2.firebase.FirestoreDBResult
import com.example.projectandroid2.firebase.IFirestoreDBRepository
import com.example.projectandroid2.mock.CountriesMock
import com.example.projectandroid2.model.Country
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FakeFirestoreDBRepositoryImpl @Inject constructor(): IFirestoreDBRepository {
    override fun getCountries(): Flow<FirestoreDBResult<List<Country?>>> {
        return callbackFlow {
            trySend(FirestoreDBResult.Success(CountriesMock.all))
            awaitClose()
        }
    }

}