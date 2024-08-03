package com.example.projectandroid2.ui.screens.settings

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.projectandroid2.architecture.BaseViewModel
import com.example.projectandroid2.datastore.DataStoreRepositoryImpl
import com.example.projectandroid2.firebase.IFirestoreDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(private val dataStore: DataStoreRepositoryImpl): BaseViewModel(){
    var name = mutableStateOf("")
    var id = mutableStateOf("")
    init {
        getName()
        getID()
    }

     fun getName() {
        launch {
            name.value = dataStore.getName()
        }
       // Log.d("DATA_STORE",name)
    }
    fun getID(){
        launch {
            id.value = dataStore.getID()
        }
    }
    fun logout(){
        launch {
            dataStore.removeLoginSuccessful()
        }
    }

}