package com.example.projectandroid2.ui.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectandroid2.R
import com.example.projectandroid2.maphelper.CustomMapRenderer
import com.example.projectandroid2.model.Country
import com.example.projectandroid2.model.Place
import com.example.projectandroid2.navigation.INavigationRouter
import com.example.projectandroid2.ui.elements.BaseScreen
import com.example.projectandroid2.ui.elements.BottomSheet
import com.example.projectandroid2.utils.JSONUtils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.GridBasedAlgorithm
import com.google.maps.android.clustering.view.ClusterRenderer
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import kotlin.reflect.KProperty


@Composable
fun MapScreen(
    navigation: INavigationRouter,
    countryInfo:String){
    val viewModel = hiltViewModel<MapScreenViewModel>()
    var places = remember {
        mutableStateListOf<Place>()
    }
    val countryJSON = JSONUtils().convertToCountryJson(countryInfo)
    viewModel.mapScreenUIState.value.let {
        when(it){
            MapScreenUIState.Default -> {
                viewModel.loadPlaces(countryJSON.name!!)
            }
            is MapScreenUIState.Success -> {
                places.clear()
                places.addAll(it.places)
            }

        }
    }
    BaseScreen(
        topBarText = stringResource(R.string.map),
        onBackClick = {navigation.navigateBack()},
        drawFullScreenContent = true
        ) {
        MapScreenContent(
            paddingValues = it,
            places = places,
            country = countryJSON,
            navigation = navigation
        )
    }
}

@Composable
fun MapScreenContent(
    paddingValues: PaddingValues,
    places: List<Place>,
    country: Country,
    navigation: INavigationRouter
){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
            val mapUiSettings by remember {
                mutableStateOf(
                    MapUiSettings(zoomControlsEnabled = false, mapToolbarEnabled = false)
                )
            }
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(LatLng(country.lat!!, country.lon!!), 6f)
            }
            var context = LocalContext.current
            var googleMap by remember { mutableStateOf<GoogleMap?>(null)  }
            var clusterManager by remember { mutableStateOf<ClusterManager<Place>?>(null) }
            var clusterRenderer by remember { mutableStateOf<ClusterRenderer<Place>?>(null) }
            var showSheet by remember { mutableStateOf(false) }
            var place by remember {
                mutableStateOf(Place(" ",0.0,0.0,"","","",""))
            }

        clusterManager?.addItems(places)
        clusterManager?.cluster()
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState

        ) {
                MapEffect(places){
                    if (googleMap == null){
                        googleMap = it
                    }
                    if (clusterManager == null){
                        clusterManager = ClusterManager<Place>(context,googleMap)
                        clusterRenderer = CustomMapRenderer(context, googleMap!!,clusterManager!!)
                        clusterManager?.apply {
                            algorithm = GridBasedAlgorithm()
                            renderer = clusterRenderer
                            renderer?.setOnClusterItemClickListener {
                                showSheet = true
                                place = it
                                true
                            }

                        }
                        clusterManager?.addItems(places)
                    }
                    googleMap?.setOnCameraIdleListener {
                        clusterManager?.cluster()
                    }
                }
            }
            if(showSheet) {
                BottomSheet(onDismiss = { showSheet = false }, place = place, navigation = navigation)
            }


    }

}
