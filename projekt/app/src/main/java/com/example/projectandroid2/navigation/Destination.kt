package com.example.projectandroid2.navigation

sealed class Destination (val route: String){
    object CountryListScreen : Destination(route = "country_list")
    object ListOfPlacesScreen : Destination(route = "places_list")
    object MapScreen: Destination(route="map")
    object AddPlaceScreen: Destination(route = "add_place")
    object MapForAddScreen: Destination(route = "lan_lon_add_place")
    object DetailScreen: Destination(route = "detail")
    object ScanScreen: Destination(route = "scan")
    object SettingsScreen: Destination(route = "settings")

}