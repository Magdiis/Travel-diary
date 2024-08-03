package com.example.projectandroid2.ui.screens.detail

sealed class DetailScreenUIState {
    object Default : DetailScreenUIState()
    object Loading: DetailScreenUIState()
    object Loaded: DetailScreenUIState()
}
