package com.example.projectandroid2.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultFromGeocoding(
    @SerializedName("plus_code")
    var plus_code: PlusCode?
)