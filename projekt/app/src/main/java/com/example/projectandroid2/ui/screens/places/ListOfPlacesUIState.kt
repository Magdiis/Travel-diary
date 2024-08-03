package com.example.projectandroid2.ui.screens.places

import com.example.projectandroid2.model.Place

sealed class ListOfPlacesUIState {
    object Default: ListOfPlacesUIState()
    class Success(val places: List<Place>): ListOfPlacesUIState()
}