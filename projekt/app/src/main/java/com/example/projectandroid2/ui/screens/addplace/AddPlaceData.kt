package com.example.projectandroid2.ui.screens.addplace

import androidx.compose.runtime.mutableStateOf
import com.example.projectandroid2.constans.Constants
import com.example.projectandroid2.model.Place

class AddPlaceData {
    var place: Place =
        Place(country = "",
            latitude = Constants.NONSENSE_LAT_AND_LON,
            longitude = Constants.NONSENSE_LAT_AND_LON,
            name = "",
            description = "",
            type = "City",
            smile = "Very satisfied"
            )
    var loading: Boolean = true
    var error: Boolean = false
}