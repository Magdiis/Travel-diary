package com.example.projectandroid2.ui.screens.mapforadd


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectandroid2.R
import com.example.projectandroid2.connection.ConnectionStatus
import com.example.projectandroid2.connection.connectivityStatus
import com.example.projectandroid2.constans.Constants
import com.example.projectandroid2.model.Country
import com.example.projectandroid2.model.ResultFromGeocoding
import com.example.projectandroid2.model.UIState
import com.example.projectandroid2.navigation.INavigationRouter
import com.example.projectandroid2.ui.elements.BaseScreen
import com.example.projectandroid2.ui.elements.NoInternetConnection
import com.example.projectandroid2.utils.JSONUtils
import com.example.projectandroid2.utils.bitmapDescriptorFromVector
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
const val TestTagButtonSaveLocationEnabled = "TestTagButtonSaveLocationEnabled"
const val TestTagButtonSaveLocationDisabled = "TestTagButtonSaveLocationDisabled"
const val TestTagBowWarning = "TestTagBowWarning"
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MapForAddScreen(
    navigation: INavigationRouter,
    countryInfo: String,
    latitudeOfMarker: Double?,
    longitudeOfMarker: Double?,
    ){
    val jsonUtils = JSONUtils()
    val country = jsonUtils.convertToCountryJson(countryInfo)
    val viewModel = hiltViewModel<MapForAddScreenViewModel>()
    val uiState: MutableState<UIState<ResultFromGeocoding,MapForAddError>> = remember{
        mutableStateOf(UIState())
    }

    viewModel.mapForAddUIState.value.let {
        uiState.value = it
    }
    

    BaseScreen(topBarText = "",
        onBackClick = {navigation.navigateBack()},
        drawFullScreenContent = true) {
        MapForAddScreenContent(
            paddingValues = it,
            country = country,
            latitudeOfMarker = if(latitudeOfMarker!=Constants.NONSENSE_LAT_AND_LON)
            {latitudeOfMarker ?: country.lat!!} else country.lat!!,
            longitudeOfMarker = if(longitudeOfMarker!=Constants.NONSENSE_LAT_AND_LON)
            {longitudeOfMarker ?: country.lon!!} else country.lon!!
            ,
            onSaveClick = {
                viewModel.loading()
                if (viewModel.locationChanged){
                    viewModel.returnInfoAboutPosition(viewModel.latitude!!, viewModel.longitude!!)
                } else {
                    viewModel.returnInfoAboutPosition(country.lat!!, country.lon!!)
                }

            },
            actions = viewModel,
            uiState = uiState.value,
            navigation = navigation,
            viewModel = viewModel,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MapForAddScreenContent(
    paddingValues: PaddingValues,
    country: Country,
    latitudeOfMarker: Double,
    longitudeOfMarker: Double,
    onSaveClick: () -> Unit,
    actions: MapForAddAction,
    uiState: UIState<ResultFromGeocoding, MapForAddError>,
    navigation: INavigationRouter,
    viewModel: MapForAddScreenViewModel,
    ){
    val mapUiSettings by remember { mutableStateOf(
        MapUiSettings(zoomControlsEnabled = false, mapToolbarEnabled = false)
    ) }
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(LatLng(country.lat!!,country.lon!!),6f)
    }

    var showBox by remember {
        mutableStateOf(false)
    }

    val connection by connectivityStatus()
    val isConnected = connection  === ConnectionStatus.Available

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)){

         GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState
        ){
             val context = LocalContext.current
            MapEffect { map ->
                val marker = map.addMarker(
                    MarkerOptions()
                        .position(map.cameraPosition.target)
                        .icon(bitmapDescriptorFromVector(context,R.drawable.custommarker))

                )
                map.setOnCameraMoveListener {
                    marker?.position = map.cameraPosition.target
                    actions.onLocationChanged(
                        latitude = map.cameraPosition.target.latitude,
                        longitude = map.cameraPosition.target.longitude
                    )
                }
            }

        }
        if (uiState.loading){
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 40.dp)
                    .size(100.dp),
                    strokeWidth = 10.dp)
            }

        }
        if(isConnected){
            if(!showBox){
                Button(onClick =  onSaveClick, enabled = true,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp)
                        .testTag(TestTagButtonSaveLocationEnabled)
                ) {
                    Text(text = stringResource(R.string.save))
                }
            }
        } else {
            NoInternetConnection()
            if(!showBox) {
                Button(
                    onClick = onSaveClick, enabled = false,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp)
                        .testTag(TestTagButtonSaveLocationDisabled)
                ) {
                    Text(text = stringResource(R.string.save))
                }
            }
        }
        if(uiState.data != null){
            LaunchedEffect(uiState.data){
                if (!viewModel.isOutside()){
                    showBox = !viewModel.checkCountry(country.name!!)
                    if (!showBox){
                        if (viewModel.locationChanged){
                            navigation.returnFromMapForAdd(latitude = viewModel.latitude!!, longitude = viewModel.longitude!!)
                        } else {
                            navigation.returnFromMapForAdd(latitude = country.lat!!, longitude = country.lon!!)
                        }
                    }
                } else {
                    showBox = true
                }

            }
        }

    if (showBox){
        Box(modifier = Modifier
            .fillMaxSize()){
            Box(modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.errorContainer)
                .testTag(TestTagBowWarning)
            )
            {
                Column(modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(painter = painterResource(id = R.drawable.info), contentDescription = null,
                        tint = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(6.dp))
                    Text(text = stringResource(R.string.warning), style = MaterialTheme.typography.headlineSmall,color =MaterialTheme.colorScheme.onErrorContainer)
                    Text(text = stringResource(R.string.you_are_outside_of_the_country), modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium )
                    Row(horizontalArrangement = Arrangement.End) {
                        Button(onClick = {
                            showBox = false
                        }, modifier = Modifier.padding(8.dp),) {
                            Text(text = stringResource(R.string.back))
                        }
                        OutlinedButton(onClick = {
                            if (viewModel.locationChanged){
                                navigation.returnFromMapForAdd(latitude = viewModel.latitude!!, longitude = viewModel.longitude!!)
                            } else {
                                navigation.returnFromMapForAdd(latitude = country.lat!!, longitude = country.lon!!)
                            }
                        }, modifier = Modifier.padding(8.dp)) {
                            Text(text = stringResource(R.string.save_anyway))
                        }
                    }
                }
            }

        }
    }
    }
}