package com.example.projectandroid2.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectandroid2.ui.screens.addplace.AddPlaceScreen
import com.example.projectandroid2.ui.screens.detail.DetailScreen
import com.example.projectandroid2.ui.screens.listofcountries.ListOfCountriesScreen
import com.example.projectandroid2.ui.screens.map.MapScreen
import com.example.projectandroid2.ui.screens.mapforadd.MapForAddScreen
import com.example.projectandroid2.ui.screens.places.ListOfPlacesScreen
import com.example.projectandroid2.ui.screens.scan.ScanScreen
import com.example.projectandroid2.ui.screens.settings.SettingsScreen
import com.example.projectandroid2.utils.JSONUtils

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navigation: INavigationRouter = remember {
        NavigationRouterImpl(navController)
    },
    startDestination: String
){
    NavHost(navController = navController, startDestination = startDestination, builder = {
        composable(Destination.CountryListScreen.route){
            ListOfCountriesScreen(navigation = navigation)
        }
        
        composable(Destination.ScanScreen.route){
            ScanScreen(navigation = navigation)
        }
        composable(Destination.SettingsScreen.route){
            SettingsScreen(navigation = navigation)
        }

        composable(Destination.MapScreen.route+ "/{countryInfo}",
            arguments = listOf(
                navArgument("countryInfo") {
                    type = NavType.StringType
                    defaultValue = ""
                })
        ){
            val countryInfo = it.arguments?.getString("countryInfo")
            MapScreen(navigation = navigation,countryInfo = countryInfo!!)
        }
        composable(Destination.DetailScreen.route+"/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                })
        ){
            val id = it.arguments?.getLong("id")
            DetailScreen(navigation = navigation,id =id!!)
        }



        composable(Destination.ListOfPlacesScreen.route + "/{countryInfo}",
            arguments = listOf(
                navArgument("countryInfo") {
                    type = NavType.StringType
                    defaultValue = ""
                })

        ){
            val countryInfo = it.arguments?.getString("countryInfo")
            ListOfPlacesScreen(countryInfo = countryInfo!!,navigation=navigation)
        }

        composable(Destination.AddPlaceScreen.route+ "/{countryInfo}",
            arguments = listOf(
                navArgument("countryInfo") {
                    type = NavType.StringType
                    defaultValue = ""
                })
            ){
            val countryInfo = it.arguments?.getString("countryInfo")
            AddPlaceScreen(navigation = navigation, countryInfo = countryInfo!!)
        }

        composable(Destination.MapForAddScreen.route +"/{countryInfo}",
            arguments = listOf(
                navArgument("countryInfo") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )){
            val countryInfo = it.arguments?.getString("countryInfo")
            MapForAddScreen(
                navigation = navigation,
                countryInfo = countryInfo!! ,
                latitudeOfMarker = null,
                longitudeOfMarker =null
            )
        }

        composable(Destination.MapForAddScreen.route+"/{countryInfo}"+"/{location}",
            arguments = listOf(
                navArgument("countryInfo"){
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("location"){
                    type = NavType.StringType
                    defaultValue = "" })
            ){
            val locationString = it.arguments?.getString("location")
            val countryInfo = it.arguments?.getString("countryInfo")
            if (!locationString.isNullOrEmpty()){
                val locationMarker = JSONUtils().covertToLocation(locationString)
                MapForAddScreen(
                    navigation = navigation,
                    latitudeOfMarker = locationMarker.latitude,
                    longitudeOfMarker = locationMarker.longitude,
                    countryInfo = countryInfo!!
                )
            } else {
                MapForAddScreen(
                    navigation = navigation,
                    latitudeOfMarker = null,
                    longitudeOfMarker = null,
                    countryInfo =  countryInfo!!
                )
            }

        }
    })
}