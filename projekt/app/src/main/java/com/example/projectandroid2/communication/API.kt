package com.example.projectandroid2.communication

import com.example.projectandroid2.model.ResultFromGeocoding
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface API {
    @Headers("Content-Type: application/json")
    @GET("json")
    suspend fun getInfoAboutPosition(@Query("latlng") latlon:String, @Query("key")key: String):Response<ResultFromGeocoding>
}