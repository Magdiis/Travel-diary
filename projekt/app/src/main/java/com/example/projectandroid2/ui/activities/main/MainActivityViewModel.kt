package com.example.projectandroid2.ui.activities.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.projectandroid2.architecture.BaseViewModel
import com.example.projectandroid2.datastore.DataStoreRepositoryImpl
import com.example.projectandroid2.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val dataStore: DataStoreRepositoryImpl) : BaseViewModel(){
    var loginFromDataStore = mutableStateOf(false)
    var loading = mutableStateOf(true)
    init {
        launch {
            loginFromDataStore.value = returnLoginSuccess()
            loading.value = false
        }
    }
    suspend fun setLoginSuccess(){
        dataStore.setLoginSuccessful()
    }

    suspend fun returnLoginSuccess(): Boolean {
        return dataStore.getLoginSuccessful()
    }
    suspend fun setName(name: String) {
        dataStore.setName(name)
    }
    suspend fun setID(id:String) {
        dataStore.setID(id)
    }
}