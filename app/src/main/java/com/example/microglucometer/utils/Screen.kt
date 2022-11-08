package com.example.microglucometer.utils

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object RegistrationScreen : Screen("registration_screen")
    object RecordsScreen : Screen("records_screen")
    object UploadImageScreen : Screen("upload_image_screen")
    object MultipleCropImageScreen : Screen("multiple_crop_image_screen")
    object ReportsScreen : Screen("reports_screen")
}