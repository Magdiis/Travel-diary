package com.example.projectandroid2.ui.screens.mapforadd

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.projectandroid2.R
import com.example.projectandroid2.architecture.BaseViewModel
import com.example.projectandroid2.architecture.CommunicationResult
import com.example.projectandroid2.architecture.IBaseRemoteRepository
import com.example.projectandroid2.communication.RemoteRepositoryImpl
import com.example.projectandroid2.model.ResultFromGeocoding
import com.example.projectandroid2.model.UIState
import com.example.projectandroid2.ui.screens.addplace.AddPlaceUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.PrivateToThis

@HiltViewModel
class MapForAddScreenViewModel @Inject constructor(private val remoteRepositoryImpl: RemoteRepositoryImpl):BaseViewModel(),MapForAddAction{
    var latitude: Double? = null
    var longitude: Double? = null
    var locationChanged = false
    var countryAPI: String = ""

    val mapForAddUIState: MutableState<UIState<ResultFromGeocoding, MapForAddError>>
            = mutableStateOf(UIState())


    override fun onLocationChanged(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
        this.locationChanged = true
    }

    override fun returnInfoAboutPosition(latitude: Double, longitude: Double) {
        launch {
            val latlon = "$latitude, $longitude"
            val result = withContext(Dispatchers.IO) {
                remoteRepositoryImpl.getInfoAboutPosition(latlon = latlon)
            }
            when(result) {
                is CommunicationResult.ConnectionError -> {
                    mapForAddUIState.value = UIState(
                        loading = false,
                        data = null,
                        errors = MapForAddError(R.string.no_internet_connection)
                    )
                }
                is CommunicationResult.Error -> {
                    mapForAddUIState.value = UIState(
                        loading = false,
                        data = null,
                        errors = MapForAddError(R.string.failed_to_load)
                    )
                }
                is CommunicationResult.Exception -> {
                    mapForAddUIState.value = UIState(
                        loading = false,
                        data = null,
                        errors = MapForAddError(R.string.unknow_error)
                    )
                }
                is CommunicationResult.Success -> {
                    mapForAddUIState.value =
                        UIState(
                            loading = false,
                            data = result.data,
                            errors = null
                        )
                    if(mapForAddUIState.value.data!!.plus_code!!.compound_code != null){
                        countryAPI = mapForAddUIState.value.data!!.plus_code!!.compound_code!!
                    }
                }
            }

        }
    }

    override fun loading() {
        mapForAddUIState.value = UIState(loading = true, data = null, errors = null)
    }

    override fun checkCountry(countryMap: String): Boolean {
        return mapForAddUIState.value.data!!.plus_code!!.compound_code!!.contains(countryMap)
    }

    override fun isOutside(): Boolean {
        return mapForAddUIState.value.data!!.plus_code!!.compound_code == null
    }


}