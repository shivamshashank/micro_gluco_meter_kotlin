package com.example.microglucometer.utils

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.microglucometer.models.User
import com.example.microglucometer.presentation.screens.*

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.RegistrationScreen.route) {
            RegistrationScreen(navController)
        }
        composable(route = Screen.RecordsScreen.route) {
            RecordsScreen(navController)
        }
        composable(route = Screen.UploadImageScreen.route) {
            val user = navController.previousBackStackEntry?.savedStateHandle?.get<User>("user")
            user?.let {
                UploadImageScreen(navController, user)
            }
        }
        composable(route = Screen.MultipleCropImageScreen.route) {
            val user = navController.previousBackStackEntry?.savedStateHandle?.get<User>("user")
            val imageUri =
                navController.previousBackStackEntry?.savedStateHandle?.get<Uri>("imageUri")
            user?.let {
                imageUri?.let {
                    MultipleCropImageScreen(navController, user, imageUri)
                }
            }
        }
        composable(route = Screen.RegionOfInterestScreen.route) {
            val user = navController.previousBackStackEntry?.savedStateHandle?.get<User>("user")
            val imageMap =
                navController.previousBackStackEntry?.savedStateHandle?.get<HashMap<String, String>>(
                    "imageMap"
                )
            user?.let {
                imageMap?.let {
                    RegionOfInterestScreen(navController, user, imageMap)
                }
            }
        }
    }
}