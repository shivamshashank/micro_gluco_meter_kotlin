package com.example.microglucometer.utils

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object RegistrationScreen : Screen("registration_screen")
    object RecordsScreen : Screen("records_screen")
    object UploadImageScreen : Screen("upload_image_screen")
    object MultipleCropImageScreen : Screen("multiple_crop_image_screen")
    object RegionOfInterestScreen : Screen("region_of_interest_screen")
//    object ReportScreen : Screen("report_screen")
}