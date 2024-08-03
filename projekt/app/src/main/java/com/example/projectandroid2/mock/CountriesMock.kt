package com.example.projectandroid2.mock

import com.example.projectandroid2.model.Country

object CountriesMock {
    val outsideOfCzechia = Country(
        name = "Czechia",
        flag = "",
        lat = 50.93991942229873,
        lon = 16.19384329766035
    )

     val czechia = Country(
        "Czechia",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Flag_of_the_Czech_Republic.svg/1280px-Flag_of_the_Czech_Republic.svg.png",
        49.8037633,
        15.4749126
    )
    private val austria = Country(
        "Austria",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Flag_of_Austria.svg/1280px-Flag_of_Austria.svg.png",
        47.6964719,
        14.3457347
    )
    private val portugal = Country(
        "Portugal",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Flag_of_Portugal.svg/1280px-Flag_of_Portugal.svg.png",
        39.557191,
        7.8536599
    )
    private val croatia = Country(
        "Croatia",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Flag_of_Croatia.svg/1280px-Flag_of_Croatia.svg.png",
        44.4737849,
        16.4688717
    )
     val spain = Country(
        "Spain",
        "https://upload.wikimedia.org/wikipedia/en/thumb/9/9a/Flag_of_Spain.svg/1280px-Flag_of_Spain.svg.png",
         40.2085,
        -3.713
    )

    val all = listOf(
        czechia,
        austria,
        portugal,
        croatia,
        spain
    )

}