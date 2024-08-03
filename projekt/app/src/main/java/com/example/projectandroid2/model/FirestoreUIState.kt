package com.example.projectandroid2.model

data class FirestoreUIState(
    var data: List<Country?>? = null,
    val isLoading: Boolean = false,
    val errorMsg: String? = null
)