package com.example.projectandroid2.ui.screens.addplace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectandroid2.R
import com.example.projectandroid2.constans.Constants
import com.example.projectandroid2.extension.round
import com.example.projectandroid2.model.Country
import com.example.projectandroid2.model.Place
import com.example.projectandroid2.model.UIState
import com.example.projectandroid2.navigation.INavigationRouter
import com.example.projectandroid2.ui.elements.BaseScreen
import com.example.projectandroid2.ui.elements.InfoElement
import com.example.projectandroid2.ui.elements.MySpinner
import com.example.projectandroid2.ui.theme.basicMargin
import com.example.projectandroid2.utils.JSONUtils

const val TestTagEmptyLocationOrName = "TestTagEmptyLocationOrName"
const val TestTagButtonForAddPlace = "TestTagButtonForAddPlace"
const val TestTagTextFieldNameForAddPlace = "TestTagTextFieldNameForAddPlace"

@Composable
fun AddPlaceScreen(
    navigation: INavigationRouter,
    countryInfo: String,
){
    val viewModel = hiltViewModel<AddPlaceViewModel>()
    var data: AddPlaceData by remember {
        mutableStateOf(viewModel.data)
    }

    val mapDataResult = navigation.getNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("location")?.observeAsState()
    mapDataResult?.value?.let {
        val location = JSONUtils().covertToLocation(it)
        if (location.latitude != Constants.NONSENSE_LAT_AND_LON){
            LaunchedEffect(location){
                viewModel.onLocationChange(latitude = location.latitude, longitude = location.longitude)
            }
        }
    }
    viewModel.addPlaceUIState.value.let {
        when(it){
            AddPlaceUIState.Default -> {

            }
            AddPlaceUIState.PlaceSaved -> {
                LaunchedEffect(it){
                    navigation.navigateBack()
                }
            }
            AddPlaceUIState.ValuesChanged -> {
                data = viewModel.data
                viewModel.addPlaceUIState.value = AddPlaceUIState.Default
            }
        }
    }

    val jsonUtils = JSONUtils()
    BaseScreen(topBarText = stringResource(id = R.string.add_place), onBackClick = {navigation.navigateBack()}) {
        AddPlaceScreenContent(countryInfo = countryInfo,
            navigation = navigation,
            data = data,
            actions = viewModel,
            country = jsonUtils.convertToCountryJson(countryInfo)
            )
    }
}

@Composable
fun AddPlaceScreenContent(
    countryInfo: String,
    navigation: INavigationRouter,
    data: AddPlaceData,
    actions: AddPlaceActions,
    country: Country
){
    var placeLength by remember {
        mutableStateOf(data.place.name)
    }
    var descriptionLength by remember {
        mutableStateOf(data.place.description)
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                InfoElement(value = if(data.place.latitude != Constants.NONSENSE_LAT_AND_LON)
                    "lat: ${data.place.latitude.round()}, lon: ${data.place.longitude.round()}" else "",
                    label = "Location",
                    leadingIcon = R.drawable.baseline_location_on_24,
                    onClick = {
                        navigation.navigateToMapForAdd(
                            latitude = data.place.latitude,
                            longitude = data.place.longitude,
                            countryInfo = countryInfo)
                    },
                    onClearClick = {
                        actions.onLocationChange(Constants.NONSENSE_LAT_AND_LON,Constants.NONSENSE_LAT_AND_LON)
                    })

                OutlinedTextField(value = data.place.name,
                    placeholder = {Text(stringResource(R.string.name))},
                    onValueChange = {
                        actions.onNameChange(it)
                        if ( it.length < 31){
                            placeLength = it
                        } },
                    maxLines = 1,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = basicMargin(), start = basicMargin(), end = basicMargin())
                        .testTag(TestTagTextFieldNameForAddPlace),
                    trailingIcon = { Text(text = placeLength.length.toString()+"/30")})

                OutlinedTextField(value = data.place.description,
                    placeholder = {Text(stringResource(R.string.description))},
                    onValueChange = {
                        actions.onDescriptionChange(it)
                        if (it.length < 101){
                            descriptionLength = it
                        } },
                    minLines = 3,
                    maxLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = basicMargin(), start = basicMargin(), end = basicMargin()),
                    trailingIcon = { Text(text = descriptionLength.length.toString()+"/100")})

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = basicMargin(), start = basicMargin(), end = basicMargin())
                ){
                    MySpinner(
                        onValueChange = {actions.onTypeChange(it)},
                        options = listOf("City","Historical Building","Forest","Mountains","Beach"),

                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = basicMargin(), start = basicMargin(), end = basicMargin())
                ){
                    MySpinner(
                        onValueChange = {actions.onSmileChange(it)},
                        options = listOf("Very satisfied","Satisfied","Neutral","Dissatisfied","Very dissatisfied"),
                    )
                }

        Button(onClick = {
            actions.savePlace(nameOfCountry = country.name!!) },
            modifier = Modifier.padding(top = 10.dp).testTag(TestTagButtonForAddPlace)) {
            Text(text = stringResource(R.string.save_place))
        }

        if(data.error){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                Icon(painter = painterResource(id = R.drawable.info), contentDescription = null,
                    tint = MaterialTheme.colorScheme.error, modifier = Modifier.padding(6.dp))
                Text(text = stringResource(R.string.location_and_name_can_t_be_empty),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag(TestTagEmptyLocationOrName))
            }

        }
    }

}