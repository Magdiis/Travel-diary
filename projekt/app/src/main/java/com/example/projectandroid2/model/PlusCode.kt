package com.example.projectandroid2.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass



@JsonClass(generateAdapter = true)
data class PlusCode(
    //does not work SerializedName. I DONT KNOW WHY.
    @SerializedName("compound_code")
    var compound_code: String?,
    @SerializedName("global_code")
    var global_code: String?
)