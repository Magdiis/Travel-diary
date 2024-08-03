package com.example.projectandroid2.placedatabase
import com.example.projectandroid2.model.Place
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlacesLocalRepositoryImpl (private val placeDao: PlacesDao): IPlacesLocalRepository {
    override fun getPlacesByCountry(countryName: String): Flow<List<Place>> {
        return placeDao.getPlacesByCountry(countryName)
    }

    override suspend fun insert(place: Place): Long {
        return placeDao.insertPlace(place)
    }

    override suspend fun getPlaceByID(id: Long): Place {
        return placeDao.getPlaceByID(id)
    }

    override suspend fun delete(place: Place) {
        placeDao.delete(place)
    }

}