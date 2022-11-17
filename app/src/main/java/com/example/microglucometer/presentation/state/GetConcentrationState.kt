package com.example.microglucometer.presentation.state

import com.example.microglucometer.models.ConcentrationModel

sealed class GetConcentrationState {
    object InitialState : GetConcentrationState()
    object LoadingState : GetConcentrationState()
    data class Success(val concentrationModel: ConcentrationModel) : GetConcentrationState()
    data class Error(val errorMessage: String) : GetConcentrationState()
}