package com.example.projectandroid2.ui.screens.listofcountries

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectandroid2.architecture.BaseViewModel
import com.example.projectandroid2.datastore.DataStoreRepositoryImpl
import com.example.projectandroid2.firebase.FirestoreDBResult
import com.example.projectandroid2.firebase.IFirestoreDBRepository
import com.example.projectandroid2.model.Country
import com.example.projectandroid2.model.FirestoreUIState
import com.example.projectandroid2.model.UIState
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfCountriesViewModel @Inject constructor(private val repository:IFirestoreDBRepository, ): BaseViewModel(){
    val countryState: StateFlow<FirestoreUIState> = repository.getCountries().map { result ->
        when (result) {
            is FirestoreDBResult.Success -> {
                FirestoreUIState(data = result.data)
            }
            is FirestoreDBResult.Error -> {
                FirestoreUIState(errorMsg = result.exception.message)
            }
            is FirestoreDBResult.Loading -> {
                FirestoreUIState(isLoading = true)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = FirestoreUIState(isLoading = true),
        started = SharingStarted.WhileSubscribed(5000)
    )


}
