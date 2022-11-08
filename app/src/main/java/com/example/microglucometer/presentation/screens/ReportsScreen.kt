package com.example.microglucometer.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.microglucometer.models.User
import com.example.microglucometer.presentation.state.GetConcentrationState
import com.example.microglucometer.view_model.GetConcentrationViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReportsScreen(
    navController: NavController,
    user: User,
    imageMap: HashMap<String, String>,
) {

    val viewModel = hiltViewModel<GetConcentrationViewModel>()

    viewModel.getConcentration(imageMap)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Reports") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) {
        val getConcentrationState by viewModel.getConcentrationState.collectAsState()

        when (getConcentrationState) {
            is GetConcentrationState.LoadingState -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is GetConcentrationState.Success -> {
                val concentration =
                    (getConcentrationState as GetConcentrationState.Success).concentrationList

                Text(text = concentration.toString())
            }
            is GetConcentrationState.Error -> {
                val message = (getConcentrationState as GetConcentrationState.Error).errorMessage
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = message)
                }
            }
            is GetConcentrationState.InitialState -> {}
        }
    }
}