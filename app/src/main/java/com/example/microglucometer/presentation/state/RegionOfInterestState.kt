package com.example.microglucometer.presentation.state

sealed class RegionOfInterestState {
    object InitialState : RegionOfInterestState()
    object LoadingState : RegionOfInterestState()
    data class Success(val regionOfInterestList: List<String>) : RegionOfInterestState()
    data class Error(val errorMessage: String) : RegionOfInterestState()
}