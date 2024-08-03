package com.example.projectandroid2.ui.screens.mapforadd

interface MapForAddAction {
    fun onLocationChanged(latitude: Double, longitude: Double)
    fun returnInfoAboutPosition(latitude: Double, longitude: Double)
    fun loading()
    fun checkCountry(countryMap: String): Boolean

    fun isOutside(): Boolean

}