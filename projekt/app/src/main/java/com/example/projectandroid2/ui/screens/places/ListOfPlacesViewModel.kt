package com.example.projectandroid2.ui.screens.places

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.projectandroid2.architecture.BaseViewModel
import com.example.projectandroid2.placedatabase.IPlacesLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfPlacesViewModel @Inject constructor(private val placeRepository: IPlacesLocalRepository): BaseViewModel(){
    val listOfPlacesUIState: MutableState<ListOfPlacesUIState> = mutableStateOf(ListOfPlacesUIState.Default)
    val loading: MutableState<Boolean> = mutableStateOf(true)
    fun loadPlaces(countryName: String){
        launch {
            placeRepository.getPlacesByCountry(countryName).collect{
                listOfPlacesUIState.value = ListOfPlacesUIState.Success(it)
            }
        }
    }
}