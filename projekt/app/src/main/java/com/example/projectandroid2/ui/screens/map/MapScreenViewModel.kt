package com.example.projectandroid2.ui.screens.map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.projectandroid2.architecture.BaseViewModel
import com.example.projectandroid2.placedatabase.IPlacesLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(private val placeRepository: IPlacesLocalRepository): BaseViewModel(){
    val mapScreenUIState: MutableState<MapScreenUIState> =
        mutableStateOf(MapScreenUIState.Default)

    fun loadPlaces(nameCountryDB: String){
        launch {
            placeRepository.getPlacesByCountry(nameCountryDB).collect{
                mapScreenUIState.value = MapScreenUIState.Success(it)
            }
        }
    }

}