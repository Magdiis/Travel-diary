package com.example.projectandroid2.ui.activities.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectandroid2.model.Place
import com.example.projectandroid2.navigation.Destination
import com.example.projectandroid2.navigation.NavGraph
import com.example.projectandroid2.ui.screens.detail.DetailScreenContent
import com.example.projectandroid2.ui.screens.listofcountries.ListOfCountriesScreen
import com.example.projectandroid2.ui.screens.places.ListOfPlacesViewModel
import com.example.projectandroid2.ui.theme.ProjectAndroid2Theme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        var success: String = ""
        var name: String = ""
        var id: String = ""
        if (bundle != null){
            success = bundle.getString("success","no success")
            name = bundle.getString("name","")
            id = bundle.getString("id","")
        }
        setContent {
            val viewModel = hiltViewModel<MainActivityViewModel>()

            ProjectAndroid2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  //  NavGraph(startDestination = Destination.CountryListScreen.route)

                    if(viewModel.loading.value){
                        Loading()
                    } else {
                        if(!viewModel.loginFromDataStore.value){
                            NavGraph(startDestination = Destination.CountryListScreen.route)
                        } else {
                            if(success == "success"){
                                LaunchedEffect(this){
                                    viewModel.setLoginSuccess()
                                    viewModel.setName(name)
                                    viewModel.setID(id)
                                }
                                NavGraph(startDestination = Destination.CountryListScreen.route)
                            } else {
                                NavGraph(startDestination = Destination.ScanScreen.route)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
 fun Loading(){
   Box(modifier = Modifier.fillMaxSize()){
       CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
   }
}



