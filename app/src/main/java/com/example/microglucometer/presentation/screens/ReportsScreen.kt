package com.example.microglucometer.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.microglucometer.models.User

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReportsScreen(
    navController: NavController,
    user: User,
) {
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
        Text(text = user.toString())
    }
}