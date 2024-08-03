package com.example.projectandroid2.ui.screens.listofcountries


import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.projectandroid2.R
import com.example.projectandroid2.connection.ConnectionStatus
import com.example.projectandroid2.connection.connectivityStatus
import com.example.projectandroid2.extension.shimmerLoadingAnimation
import com.example.projectandroid2.model.Country
import com.example.projectandroid2.model.FirestoreUIState
import com.example.projectandroid2.navigation.INavigationRouter
import com.example.projectandroid2.ui.elements.BaseScreen
import com.example.projectandroid2.ui.elements.CountryRow
import com.example.projectandroid2.ui.elements.MySearchBar
import com.example.projectandroid2.ui.elements.NoInternetConnection
import com.example.projectandroid2.utils.JSONUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi


const val TestTagListOfCountriesLazyList = "TestTagListOfCountriesLazyList"


@ExperimentalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ListOfCountriesScreen(
    navigation: INavigationRouter
){
    val viewModel = hiltViewModel<ListOfCountriesViewModel>()
    val countryState by viewModel.countryState.collectAsStateWithLifecycle()
    val jsonUtils = JSONUtils()
    BaseScreen(topBarText = stringResource(R.string.countries),
        showLoading = countryState.isLoading,
        drawFullScreenContent = true,
        actions = {
            IconButton(onClick = { navigation.navigateToSettingsScreen() }) {
                Icon(painterResource(R.drawable.baseline_manage_accounts_24), contentDescription = null, tint = Color.Black)
            }

        }) {
        ListOfCountriesScreenContent(
            countryState = countryState,
            paddingValues = it,
            navigation = navigation,
            jsonUtils = jsonUtils
        )
    }


}

@ExperimentalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ListOfCountriesScreenContent(
     countryState: FirestoreUIState,
     paddingValues: PaddingValues,
     navigation: INavigationRouter,
     jsonUtils: JSONUtils,
){
            val connection by connectivityStatus()
            val isConnected = connection  === ConnectionStatus.Available

           when {
            countryState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize()){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            !countryState.errorMsg.isNullOrEmpty() -> {
                if(!isConnected){
                    Box(modifier = Modifier.padding(paddingValues)) {
                        NoInternetConnection()
                    }
                }
            }
            countryState.data.isNullOrEmpty() -> {
                if(!isConnected){
                    Box(modifier = Modifier.padding(paddingValues)){
                        NoInternetConnection()
                    }
                }

            }
            !countryState.data.isNullOrEmpty() -> {

                countryState.data = countryState.data!!.sortedBy {
                    it!!.name!!
                }
                Column(modifier = Modifier.padding(paddingValues)) {
                    if(!isConnected){
                        NoInternetConnection()
                    }
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center){
                        MySearchBar(countries = countryState.data, navigation = navigation)
                    }

                    LazyColumn(modifier = Modifier
                        .padding(top=5.dp)
                        .testTag(TestTagListOfCountriesLazyList)) {
                        countryState.data!!.forEach{
                            item{
                                if (it != null){
                                    CountryRow(it, navigation)
                                }
                            }

                        }
                    }
                }

            }
        }
    }



