package com.example.projectandroid2.ui.screens.map

import com.example.projectandroid2.model.Place

sealed class MapScreenUIState {
    object Default: MapScreenUIState()
    class Success(val places: List<Place>): MapScreenUIState()
}
