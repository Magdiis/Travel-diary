package com.example.projectandroid2.model

import com.example.projectandroid2.constans.Constants
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country(
    @Json(name="name")
    var name: String? = "",
    @Json(name="flag")
    var flag: String? = "",
    @Json(name="lat")
    var lat: Double? = Constants.NONSENSE_LAT_AND_LON,
    @Json(name="lon")
    var lon: Double? = Constants.NONSENSE_LAT_AND_LON
)