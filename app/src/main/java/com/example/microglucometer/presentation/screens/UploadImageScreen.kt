package com.example.microglucometer.presentation.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.microglucometer.BuildConfig
import com.example.microglucometer.models.User
import com.example.microglucometer.utils.Screen
import java.io.File
import java.util.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UploadImageScreen(
    navController: NavController,
    user: User,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Upload Image") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        UploadImageBody(navController, user)
    }
}

@Composable
fun UploadImageBody(
    navController: NavController,
    user: User,
) {
    val context = LocalContext.current

    var isCameraSelected = false

    val cameraImageUri: Uri = FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        File("${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath}/${System.currentTimeMillis()}.jpg")
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) {
        if (it) {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "user",
                user,
            )
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "imageUri",
                cameraImageUri,
            )
            navController.navigate(Screen.MultipleCropImageScreen.route)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "user",
                user,
            )
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "imageUri",
                it,
            )
            navController.navigate(Screen.MultipleCropImageScreen.route)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (isCameraSelected) {
                cameraLauncher.launch(cameraImageUri)
            } else {
                galleryLauncher.launch("image/*")
            }
        } else {
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Button(
            onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA,
                    ),
                    -> {
                        cameraLauncher.launch(cameraImageUri)
                    }
                    else -> {
                        isCameraSelected = true
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(48.dp),
        ) {
            Row {
                Icon(
                    imageVector = Icons.Filled.Camera,
                    contentDescription = "Capture Image Icon",
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "Capture Image",
                    fontSize = 18.sp,
                )
            }
        }

        Text(
            text = "OR",
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
        )

        Button(
            onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                    ),
                    -> {
                        galleryLauncher.launch("image/*")
                    }
                    else -> {
                        isCameraSelected = false
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(48.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Folder,
                contentDescription = "Browse Image Icon",
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "Browse Image",
                fontSize = 18.sp,
            )
        }
    }

}
