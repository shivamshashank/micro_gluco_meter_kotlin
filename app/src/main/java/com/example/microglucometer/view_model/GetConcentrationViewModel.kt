package com.example.microglucometer.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.microglucometer.network.ApiService
import com.example.microglucometer.presentation.state.GetConcentrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GetConcentrationViewModel @Inject constructor(
    private val apiService: ApiService,
) : ViewModel() {

    val getConcentrationState =
        MutableStateFlow<GetConcentrationState>(GetConcentrationState.InitialState)

    fun getConcentration(
        imageMap: HashMap<String, String>
    ) {
        viewModelScope.launch {
            getConcentrationState.tryEmit(GetConcentrationState.LoadingState)
            withContext(Dispatchers.IO) {
                try {
                    val concentrationList = apiService.getConcentration(
                        imageMap["image_string1"]!!,
                        imageMap["image_string2"]!!,
                        imageMap["image_string3"]!!,
                    )

                    getConcentrationState.tryEmit(GetConcentrationState.Success(concentrationList))
                } catch (e: Exception) {
                    getConcentrationState.tryEmit(
                        GetConcentrationState.Error(
                            e.message ?: "An Unknown Error Occurred"
                        )
                    )
                }
            }
        }
    }
}