package com.example.projectandroid2.fake


import com.example.projectandroid2.architecture.CommunicationResult
import com.example.projectandroid2.communication.IRemoteRepository
import com.example.projectandroid2.firebase.FirestoreDBResult
import com.example.projectandroid2.firebase.IFirestoreDBRepository
import com.example.projectandroid2.mock.CountriesMock
import com.example.projectandroid2.mock.GeocodingMock
import com.example.projectandroid2.mock.PlaceMock
import com.example.projectandroid2.model.Country
import com.example.projectandroid2.model.ResultFromGeocoding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FakeRemoteRepositoryImpl @Inject constructor(): IRemoteRepository {
    override suspend fun getInfoAboutPosition(latlon: String):
            CommunicationResult<ResultFromGeocoding> {
//        val latlonSpain = "${CountriesMock.spain.lat},${CountriesMock.spain.lon}"
//        if(latlon == latlonSpain){
            return CommunicationResult.Success(GeocodingMock.spain)
//        } else {
//            return CommunicationResult.Success(GeocodingMock.czechia)
//        }

    }
}