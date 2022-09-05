package com.example.microglucometer.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import com.example.microglucometer.models.User
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun MultipleCropImageScreen(
    navigator: DestinationsNavigator,
    user: User,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Crop Image") },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        MultipleCropImageBody()
    }
}

@Composable
fun MultipleCropImageBody() {

}
