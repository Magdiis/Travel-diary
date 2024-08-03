package com.example.projectandroid2.navigation

import androidx.navigation.NavController
import com.example.projectandroid2.model.Place

interface INavigationRouter {
    fun navigateBack()
    fun getNavController(): NavController

    fun navigateToListOfPlacesScreen(countryInfo: String)

    fun navigateToAddPLaceScreen(countryInfo: String)

    fun navigateToMapForAdd(latitude: Double?,longitude:Double?,countryInfo: String)
    fun returnFromMapForAdd(latitude:Double, longitude: Double)
    fun navigateToListOfCountries()
    fun navigateToMapScreen(countryInfo: String)
    fun navigateToDetail(id: Long)
    fun navigateToSettingsScreen()

}