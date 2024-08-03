package com.example.projectandroid2.utils

import android.net.Uri
import com.example.projectandroid2.constans.Constants.NONSENSE_LAT_AND_LON
import com.example.projectandroid2.model.Country
import com.example.projectandroid2.model.Location
import com.google.gson.Gson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class JSONUtils {
//    private val moshi: Moshi = Moshi.Builder()
//        .addLast(KotlinJsonAdapterFactory())
//        .build()
//    private val jsonAdapter: JsonAdapter<Country> = moshi.adapter(Country::class.java)
//    private val jsonAdapterLocation: JsonAdapter<Location> = moshi.adapter(Location::class.java )

    private val gson = Gson()
    fun convertCountryToString(country: Country): String {
//        return  jsonAdapter.toJson(
//            Country(
//            name = country.name,
//            flag = "", // url encode
//            lat = country.lat,
//            lon = country.lon
//        )
//        )
        country.flag = ""
        return gson.toJson(country)
    }

    fun convertToCountryJson(country: String): Country{
        //return jsonAdapter.fromJson(country) ?: Country(name = null,flag = null,lat = null,lon = null)
        return gson.fromJson(country,Country::class.java)
    }

    fun covertToLocation(location: String): Location {
       // return jsonAdapterLocation.fromJson(location) ?: Location(NONSENSE_LAT_AND_LON,NONSENSE_LAT_AND_LON)
        return gson.fromJson(location, Location::class.java)
    }

    fun covertLocationToString(location: Location):String{
//        return jsonAdapterLocation.toJson(
//            Location(location.latitude,location.longitude))
       return gson.toJson(location)
    }



}