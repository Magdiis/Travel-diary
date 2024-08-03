package com.example.projectandroid2.placedatabase

import com.example.projectandroid2.model.Place
import kotlinx.coroutines.flow.Flow

interface IPlacesLocalRepository {
    fun getPlacesByCountry(countryName: String): Flow<List<Place>>
    suspend fun insert(place: Place): Long

    suspend fun getPlaceByID(id:Long): Place
    suspend fun delete(place: Place)
}