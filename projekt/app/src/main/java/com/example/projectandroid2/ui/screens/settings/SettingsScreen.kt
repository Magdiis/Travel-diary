package com.example.projectandroid2.ui.screens.settings

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectandroid2.R
import com.example.projectandroid2.navigation.INavigationRouter
import com.example.projectandroid2.ui.activities.camera.CameraXLivePreviewActivity
import com.example.projectandroid2.ui.activities.main.MainActivity
import com.example.projectandroid2.ui.elements.BaseScreen
import com.example.projectandroid2.ui.screens.listofcountries.ListOfCountriesViewModel
import kotlinx.coroutines.coroutineScope

@Composable
fun SettingsScreen(
    navigation:INavigationRouter
){
    val viewModel = hiltViewModel<SettingsScreenViewModel>()
    BaseScreen(topBarText = "", onBackClick = {navigation.navigateBack()}, drawFullScreenContent = true) {
        SettingsScreenContent(
            it,
            viewModel,
            navigation
        )
    }
}
@Composable
fun SettingsScreenContent(
    paddingValues: PaddingValues,
    viewModel: SettingsScreenViewModel,
    navigation: INavigationRouter
) {
    val context = LocalContext.current
    val activity: Activity = context as Activity
    Column( horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Row {
            Column(modifier = Modifier.padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.profile_two), contentDescription = null,
                    modifier = Modifier.padding(16.dp))
                Text(text = viewModel.name.value, style = MaterialTheme.typography.headlineMedium)
                Text(text ="ID: "+ viewModel.id.value, style = MaterialTheme.typography.bodyMedium)
            }
        }
        Button(onClick = {
            viewModel.logout()
            context.startActivity(
                Intent(context, MainActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
        }, modifier = Modifier.padding(top = 32.dp) ) {
            Text(text = stringResource(R.string.log_out))
        }
    }
    }

