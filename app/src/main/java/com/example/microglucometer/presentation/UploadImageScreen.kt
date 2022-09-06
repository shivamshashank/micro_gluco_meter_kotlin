package com.example.microglucometer.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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
import com.example.microglucometer.methods.ImageConversion
import com.example.microglucometer.models.UploadImage
import com.example.microglucometer.models.User
import com.example.microglucometer.presentation.destinations.MultipleCropImageScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun UploadImageScreen(
    navigator: DestinationsNavigator,
    user: User,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Upload Image") },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        UploadImageBody(navigator, user)
    }
}

@Composable
fun UploadImageBody(
    navigator: DestinationsNavigator,
    user: User,
) {
    val context = LocalContext.current

    var isCameraSelected = false

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { btm: Bitmap? ->

        btm?.let { it ->
            val originalImageByteArray = ImageConversion().convertBitmapToByteArrays(it)

            navigator.navigate(
                MultipleCropImageScreenDestination(
                    user,
                    uploadImage = UploadImage(originalImageByteArray),
                )
            )
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        var bitmap: Bitmap? = null

        uri?.let {
            if (!isCameraSelected) {
                bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    ImageDecoder.decodeBitmap(source)
                }
            }

            bitmap?.let { bitmap ->
                val originalImageByteArray = ImageConversion().convertBitmapToByteArrays(bitmap)

                navigator.navigate(
                    MultipleCropImageScreenDestination(
                        user,
                        uploadImage = UploadImage(originalImageByteArray),
                    )
                )
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (isCameraSelected) {
                cameraLauncher.launch()
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
                        cameraLauncher.launch()
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