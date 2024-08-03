package com.example.projectandroid2.ui.screens.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.projectandroid2.architecture.BaseViewModel
import com.example.projectandroid2.model.Place
import com.example.projectandroid2.placedatabase.IPlacesLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val placeRepository: IPlacesLocalRepository):
    BaseViewModel(), DetailScreenActions{
    val detailScreenUIState: MutableState<DetailScreenUIState> =
        mutableStateOf(DetailScreenUIState.Loading)
    var data: DetailScreenData = DetailScreenData()


    fun loadPlace(id: Long){
        launch {
            val place: Place = placeRepository.getPlaceByID(id)
            data.place = place
            data.loading = false
            detailScreenUIState.value = DetailScreenUIState.Loaded

        }
    }
    override fun deletePlace() {
        launch {
            placeRepository.delete(data.place)
        }
    }

}