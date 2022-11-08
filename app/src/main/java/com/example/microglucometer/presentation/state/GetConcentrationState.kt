package com.example.microglucometer.presentation.state

sealed class GetConcentrationState {
    object InitialState : GetConcentrationState()
    object LoadingState : GetConcentrationState()
    data class Success(val concentrationList: List<String>) : GetConcentrationState()
    data class Error(val errorMessage: String) : GetConcentrationState()
}