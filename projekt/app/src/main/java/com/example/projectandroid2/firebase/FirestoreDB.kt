package com.example.projectandroid2.firebase

import com.example.projectandroid2.model.Country
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreDB @Inject constructor(private val reference: CollectionReference){
    fun getCountries(): Flow<FirestoreDBResult<List<Country>>> = callbackFlow {
        trySend(FirestoreDBResult.Loading)
        val listener = reference.addSnapshotListener{ value, error ->
            if (error != null) trySend(FirestoreDBResult.Error(Throwable(error.message)))
            if (value != null){
                val countries = value.map { documentSnapshot ->
                    documentSnapshot.toObject<Country>()
                }
                trySend(FirestoreDBResult.Success(countries))
            }
        }
        awaitClose{listener.remove()}
    }
}