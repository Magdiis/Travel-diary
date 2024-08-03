package com.example.projectandroid2.navigation

import androidx.navigation.NavController
import com.example.projectandroid2.model.Location
import com.example.projectandroid2.model.Place
import com.example.projectandroid2.utils.JSONUtils

class NavigationRouterImpl (private val navController: NavController): INavigationRouter{
    override fun navigateBack() {
        navController.popBackStack()
    }

    override fun getNavController(): NavController {
        return navController
    }

    override fun navigateToListOfPlacesScreen(countryInfo: String) {
        navController.navigate(Destination.ListOfPlacesScreen.route + "/" + countryInfo)
    }

    override fun navigateToAddPLaceScreen(countryInfo: String) {
        navController.navigate(Destination.AddPlaceScreen.route+"/"+countryInfo)
    }

    override fun navigateToMapForAdd(latitude: Double?, longitude: Double?, countryInfo: String) {
        if (latitude != null && longitude != null){
            val locationString = JSONUtils().covertLocationToString(location = Location(latitude,longitude))
            navController.navigate(Destination.MapForAddScreen.route+"/"+countryInfo+"/"+locationString)
        } else {
            navController.navigate(Destination.MapForAddScreen.route+"/"+countryInfo)
        }

    }

    override fun returnFromMapForAdd(latitude: Double, longitude: Double) {
        val locationString = JSONUtils().covertLocationToString(location = Location(latitude,longitude))
        navController.previousBackStackEntry?.savedStateHandle?.set(
            "location",locationString
        )
        navigateBack()

    }

    override fun navigateToListOfCountries() {
        navController.navigate(Destination.CountryListScreen.route)
    }

    override fun navigateToMapScreen(countryInfo: String) {
        navController.navigate(Destination.MapScreen.route+"/"+countryInfo)
    }

    override fun navigateToDetail(id: Long) {
        navController.navigate(Destination.DetailScreen.route+"/"+id)
    }

    override fun navigateToSettingsScreen() {
        navController.navigate(Destination.SettingsScreen.route)
    }


}