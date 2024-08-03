package com.example.projectandroid2.ui.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectandroid2.R
import com.example.projectandroid2.extension.round
import com.example.projectandroid2.model.Place
import com.example.projectandroid2.navigation.INavigationRouter
import com.example.projectandroid2.ui.elements.BaseScreen
import com.example.projectandroid2.ui.elements.LoadingScreen
import com.example.projectandroid2.ui.elements.returnPlaceIcon
import com.example.projectandroid2.ui.elements.returnSmileIcon
import com.example.projectandroid2.ui.screens.places.ListOfPlacesViewModel
import com.example.projectandroid2.ui.theme.primaryColor
import com.example.projectandroid2.utils.bitmapDescriptorFromVector
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


const val TestTagDescription = "TestTagDescription"
@Composable
fun DetailScreen(
    navigation: INavigationRouter,
    id: Long,
){
    val viewModel = hiltViewModel<DetailScreenViewModel>()
    var data: DetailScreenData by remember {
        mutableStateOf(viewModel.data)
    }
    viewModel.detailScreenUIState.value.let {
        when(it){
            DetailScreenUIState.Default -> {
            }
            DetailScreenUIState.Loading -> {
                viewModel.loadPlace(id)
            }
            DetailScreenUIState.Loaded -> {
                data = viewModel.data
                viewModel.detailScreenUIState.value = DetailScreenUIState.Default

            }

        }
    }

    if(data.loading){
        LoadingScreen(modifier = Modifier)
    } else {
        DetailScreenContent(place = data.place, actions = viewModel, navigation = navigation)
    }
}

@Composable
fun DetailScreenContent(
    place: Place,
    actions: DetailScreenActions,
    navigation: INavigationRouter
) {
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false,
                zoomGesturesEnabled = false,
                rotationGesturesEnabled = false,
                scrollGesturesEnabled = false,
                scrollGesturesEnabledDuringRotateOrZoom = false,
                mapToolbarEnabled = false
            )
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(place.latitude, place.longitude), 6f)
    }
    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                GoogleMap(
                    uiSettings = mapUiSettings,
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(LatLng(place.latitude, place.longitude)),
                        draggable = false,
                        icon = bitmapDescriptorFromVector(LocalContext.current,R.drawable.custommarker)
                    )
                }
                Column {
                    FilledIconButton(onClick = {navigation.navigateBack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }

                }
            }

            Text(text = place.name, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(top = 16.dp))
        }
        Column(modifier = Modifier.padding(16.dp)) {
            rowIconText(text = "lat: ${place.latitude.round()}, lon: ${place.longitude.round()}",
                myPainterResource = R.drawable.baseline_location_on_24)
            rowIconText(text = place.type, myPainterResource = returnPlaceIcon(place.type))
            rowIconText(text = place.smile, myPainterResource = returnSmileIcon(place.smile))

            if(place.description.isNotEmpty()){
                Card(modifier = Modifier.fillMaxWidth().testTag(TestTagDescription)){
                    Text(text = place.description, modifier = Modifier.padding(12.dp))
                }

            } else {
                //Text("no description")
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)){
                OutlinedButton(onClick = {
                    actions.deletePlace()
                    navigation.navigateBack()
                }, colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Icon(Icons.Filled.Delete, contentDescription =null )
                    Text(text = stringResource(R.string.delete))
                }
            }

        }
    }

    }

@Composable
fun rowIconText(text:String, myPainterResource:Int){
    Row(modifier = Modifier.padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = myPainterResource), contentDescription =null,
            tint = primaryColor, modifier = Modifier
                .size(40.dp)
                .padding(end = 8.dp))
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}