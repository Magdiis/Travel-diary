package com.example.projectandroid2.datastore

interface IDataStoreRepository {
    suspend fun setLoginSuccessful()
    suspend fun getLoginSuccessful(): Boolean
    suspend fun removeLoginSuccessful()
    suspend fun setName(name: String)
    suspend fun getName(): String
    suspend fun setID(id: String)
    suspend fun getID(): String
}
