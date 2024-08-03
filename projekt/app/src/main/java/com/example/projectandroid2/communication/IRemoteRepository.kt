package com.example.projectandroid2.communication

import com.example.projectandroid2.architecture.CommunicationResult
import com.example.projectandroid2.architecture.IBaseRemoteRepository
import com.example.projectandroid2.model.ResultFromGeocoding

interface IRemoteRepository: IBaseRemoteRepository {
    suspend fun getInfoAboutPosition(latlon:String): CommunicationResult<ResultFromGeocoding>
}