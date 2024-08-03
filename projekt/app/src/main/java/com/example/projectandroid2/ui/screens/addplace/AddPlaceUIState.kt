package com.example.projectandroid2.ui.screens.addplace

sealed class AddPlaceUIState {
    object Default: AddPlaceUIState()
    object PlaceSaved: AddPlaceUIState()
    object ValuesChanged: AddPlaceUIState()
}