package com.example.projectandroid2.ui.screens.addplace

interface AddPlaceActions {
    fun savePlace(nameOfCountry: String)
    fun onLocationChange(latitude: Double?, longitude: Double?)
    fun onNameChange(name: String)
    fun onDescriptionChange(description: String)
    fun onTypeChange(type: String)
    fun onSmileChange(smile: String)



}