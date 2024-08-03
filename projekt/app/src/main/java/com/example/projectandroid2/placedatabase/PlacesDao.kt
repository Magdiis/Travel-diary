package com.example.projectandroid2.placedatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.projectandroid2.model.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacesDao {
    @Query("SELECT * FROM places WHERE country = :countryName")
    fun getPlacesByCountry(countryName:String): Flow<List<Place>>
    @Insert
    suspend fun insertPlace(place: Place):Long

    @Query("SELECT * FROM places WHERE id = :id")
    suspend fun getPlaceByID(id:Long): Place

    @Delete
    suspend fun delete(place: Place)


}