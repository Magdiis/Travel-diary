package com.example.projectandroid2.ui.screens.addplace

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.projectandroid2.architecture.BaseViewModel
import com.example.projectandroid2.constans.Constants
import com.example.projectandroid2.model.Place
import com.example.projectandroid2.placedatabase.IPlacesLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlaceViewModel @Inject constructor(private val placeRepository: IPlacesLocalRepository): BaseViewModel(), AddPlaceActions {
    var addPlaceUIState: MutableState<AddPlaceUIState> = mutableStateOf(AddPlaceUIState.Default)
    var data = AddPlaceData()
    override fun savePlace(nameOfCountry: String) {
        data.place.country = nameOfCountry
        launch {
            if (data.place.name == "" ||
                data.place.latitude == Constants.NONSENSE_LAT_AND_LON
            ) {
                data.error = true
                addPlaceUIState.value = AddPlaceUIState.ValuesChanged
            } else {
                val id = placeRepository.insert(data.place)
                if (id > 0) {
                    addPlaceUIState.value = AddPlaceUIState.PlaceSaved
                }
            }
        }
    }

    override fun onLocationChange(latitude: Double?, longitude: Double?) {
        if (latitude != null && longitude != null){
            data.place.latitude = latitude
            data.place.longitude = longitude
            addPlaceUIState.value = AddPlaceUIState.ValuesChanged
        }


    }

    override fun onNameChange(name: String) {
        if (name.length <=30) {
            data.place.name = name
            addPlaceUIState.value = AddPlaceUIState.ValuesChanged
        } else {
            addPlaceUIState.value = AddPlaceUIState.ValuesChanged
        }

    }


    override fun onDescriptionChange(description: String) {
        if (description.length <= 100){
            data.place.description = description
            addPlaceUIState.value = AddPlaceUIState.ValuesChanged
        } else {
            addPlaceUIState.value = AddPlaceUIState.ValuesChanged
        }

    }

    override fun onTypeChange(type: String) {
        data.place.type = type
        addPlaceUIState.value = AddPlaceUIState.ValuesChanged
    }

    override fun onSmileChange(smile: String) {
        data.place.smile = smile
        addPlaceUIState.value = AddPlaceUIState.ValuesChanged
    }
}