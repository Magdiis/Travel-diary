package com.example.projectandroid2.communication

import com.example.projectandroid2.architecture.CommunicationResult
import com.example.projectandroid2.mock.GeocodingMock
import com.example.projectandroid2.model.ResultFromGeocoding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RemoteRepositoryImpl @Inject constructor(private val api: API):IRemoteRepository {
    override suspend fun getInfoAboutPosition(
        latlon: String
    ): CommunicationResult<ResultFromGeocoding> {
        return processResponse(
            withContext(Dispatchers.IO) {
                api.getInfoAboutPosition(latlon, key = "KEY")
            }
        )
    }
}