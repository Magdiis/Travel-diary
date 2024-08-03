package com.example.projectandroid2.fake

import com.example.projectandroid2.mock.PlaceMock
import com.example.projectandroid2.model.Place
import com.example.projectandroid2.placedatabase.IPlacesLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakePLacesLocalRepository @Inject constructor():IPlacesLocalRepository {
    override fun getPlacesByCountry(countryName: String): Flow<List<Place>> {
        if(countryName == PlaceMock.prague.country){
            return flowOf(PlaceMock.czechPlaces)
        } else {
            return flowOf(listOf())
        }
    }

    override suspend fun insert(place: Place): Long {
        return 1L
    }

    override suspend fun getPlaceByID(id: Long): Place {
        if (id == 1L){
            return PlaceMock.brno
        } else {
            return PlaceMock.prague
        }
    }

    override suspend fun delete(place: Place) {

    }

}