package com.example.projectandroid2.mock

import com.example.projectandroid2.model.Country
import com.example.projectandroid2.model.Place

object PlaceMock {
     val prague = Place(
        "Czechia",
         50.073658,
         14.418540,
         "Prague",
         "",
         "City",
         "Satisfied"
    )
    val brno = Place(
        "Czechia",
        49.1953,
        16.6083,
        "Brno",
        "Very nice",
        "Forest",
        "Neutral",
    )

    val czechPlaces = listOf(brno, prague)

}

