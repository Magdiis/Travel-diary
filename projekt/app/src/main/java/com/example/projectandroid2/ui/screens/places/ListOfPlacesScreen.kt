package com.example.projectandroid2.ui.screens.places

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectandroid2.R
import com.example.projectandroid2.model.Country
import com.example.projectandroid2.model.Place
import com.example.projectandroid2.navigation.INavigationRouter
import com.example.projectandroid2.ui.elements.BaseScreen
import com.example.projectandroid2.ui.elements.LoadingScreen
import com.example.projectandroid2.ui.elements.PlaceRow
import com.example.projectandroid2.ui.screens.listofcountries.TestTagListOfCountriesLazyList
import com.example.projectandroid2.ui.theme.basicMargin
import com.example.projectandroid2.utils.JSONUtils


const val TestTagButtonAddPlace = "TestTagButtonAddPlace"
const val TestTagListOfPLaces = "TestTagListOfPLaces"

@Composable
fun ListOfPlacesScreen(
    countryInfo:String,
    navigation:INavigationRouter
){
    val viewModel = hiltViewModel<ListOfPlacesViewModel>()
    val jsonUtils = JSONUtils()
    val country = jsonUtils.convertToCountryJson(countryInfo)
    var places = remember {
        mutableStateListOf<Place>()
    }

    viewModel.listOfPlacesUIState.value.let {
        when(it){
            ListOfPlacesUIState.Default -> {
                viewModel.loadPlaces(country.name!!)
            }
            is ListOfPlacesUIState.Success -> {
                places.clear()
                places.addAll(it.places)
                viewModel.loading.value = false
            }
        }
    }
    BaseScreen(topBarText = country.name ?: "Country",
        onBackClick = {navigation.navigateBack()},
        showLoading = viewModel.loading.value,
        drawFullScreenContent = true,
        floatingActionButton = {
            if(places.isNotEmpty()){
                FloatingActionButton(
                    onClick = {
                        navigation.navigateToAddPLaceScreen(countryInfo)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        //tint = Color.White
                    )
                }
            }
        },
        actions = {
            if(places.isNotEmpty()) {
                IconButton(onClick = { navigation.navigateToMapScreen(countryInfo = countryInfo) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_map_24),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }

        }) {
        ListOfPLacesScreenContent(
            countryInfo = countryInfo,
            country = country,
            paddingValues = it,
            navigation=navigation,
            places = places,
        )
    }
}

@Composable
fun ListOfPLacesScreenContent(
    countryInfo: String,
    country: Country,
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    places: MutableList<Place>,
){
        if (places.isNotEmpty()) {
            LazyColumn( modifier = Modifier.padding(paddingValues).testTag(TestTagListOfPLaces)) {
                places.forEach{
                    item {
                        PlaceRow(place = it,navigation = navigation)
                    }
                }
            }
        } else {
            Column(modifier = Modifier.padding(paddingValues).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
               /* verticalArrangement = Arrangement.Center */) {
                Image(painterResource(id = R.drawable.no_places),null, modifier = Modifier.padding(16.dp))
                Text(text = stringResource(R.string.your_list_of_places_is_empty), style = MaterialTheme.typography.titleMedium)
                Button(onClick = {
                    navigation.navigateToAddPLaceScreen(countryInfo)
                }, modifier = Modifier.padding(top = 64.dp).testTag(TestTagButtonAddPlace)) {
                    Text(stringResource(R.string.add_place))
                }
            }

        }
    }