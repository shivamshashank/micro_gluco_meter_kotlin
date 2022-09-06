package com.example.microglucometer.presentation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.microglucometer.methods.ImageConversion
import com.example.microglucometer.models.UploadImage
import com.example.microglucometer.models.User
import com.example.microglucometer.presentation.destinations.ReportsScreenDestination
import com.example.microglucometer.ui.theme.Brown500
import com.example.microglucometer.ui.theme.Brown700
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun MultipleCropImageScreen(
    navigator: DestinationsNavigator,
    user: User,
    uploadImage: UploadImage,
) {
    val originalImageBitmap = ImageConversion()
        .convertByteArraysToBitmap(uploadImage.originalImageByteArray)

    val context = LocalContext.current

    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(10f, 10f),
            0f,
        )
    )

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    val crop1 = rememberSaveable { mutableStateOf(originalImageBitmap) }
    val crop2 = rememberSaveable { mutableStateOf(originalImageBitmap) }
    val crop3 = rememberSaveable { mutableStateOf(originalImageBitmap) }

    val imageCropLauncher1 =
        rememberLauncherForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                // use the cropped image
                crop1.value = ImageConversion().convertUriToBitmap(context, result.uriContent!!)
            } else {
                // an error occurred cropping
                val exception = result.error
                Toast.makeText(context, exception?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    val imageCropLauncher2 =
        rememberLauncherForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                // use the cropped image
                crop2.value = ImageConversion().convertUriToBitmap(context, result.uriContent!!)
            } else {
                // an error occurred cropping
                val exception = result.error
                Toast.makeText(context, exception?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    val imageCropLauncher3 =
        rememberLauncherForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                // use the cropped image
                crop3.value = ImageConversion().convertUriToBitmap(context, result.uriContent!!)
            } else {
                // an error occurred cropping
                val exception = result.error
                Toast.makeText(context, exception?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }

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
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 0.05.dw),
                backgroundColor = Color.White,
                elevation = 0.dp,
                contentPadding = PaddingValues(0.dp),
            ) {
                Button(
                    onClick = {
                        if (crop1.value == originalImageBitmap || crop2.value == originalImageBitmap || crop3.value == originalImageBitmap) {
                            Toast
                                .makeText(context, "Please crop all 3 Images", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            navigator.navigate(
                                ReportsScreenDestination(user)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                ) {
                    Text(
                        text = "Continue",
                        fontSize = 18.sp,
                    )
                }
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .padding(vertical = 24.dp, horizontal = 0.05.dw)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                if (crop1.value == originalImageBitmap) {
                    Box(Modifier.size(0.25.dw), contentAlignment = Alignment.Center) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawRoundRect(color = Brown500, style = stroke)
                        }
                        IconButton(modifier = Modifier.then(Modifier.size(48.dp)),
                            onClick = {
                                imageCropLauncher1.launch(
                                    CropImageContractOptions(
                                        ImageConversion().convertBitmapToUri(
                                            context,
                                            originalImageBitmap,
                                        ),
                                        CropImageOptions(),
                                    ),
                                )
                            }) {
                            Icon(
                                Icons.Filled.Camera,
                                "Camera Icon",
                                tint = Brown500,
                            )
                        }
                    }
                } else {
                    Image(
                        bitmap = crop1.value.asImageBitmap(),
                        contentDescription = "Image",
                        alignment = Alignment.TopCenter,
                        modifier = Modifier
                            .size(0.25.dw)
                            .clip(RoundedCornerShape(percent = 10)),
                        contentScale = ContentScale.FillBounds,
                    )
                }

                if (crop2.value == originalImageBitmap) {
                    Box(Modifier.size(0.25.dw), contentAlignment = Alignment.Center) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawRoundRect(color = Brown500, style = stroke)
                        }
                        IconButton(modifier = Modifier.then(Modifier.size(48.dp)),
                            onClick = {
                                imageCropLauncher2.launch(
                                    CropImageContractOptions(
                                        ImageConversion().convertBitmapToUri(
                                            context,
                                            originalImageBitmap,
                                        ),
                                        CropImageOptions(),
                                    ),
                                )
                            }) {
                            Icon(
                                Icons.Filled.Camera,
                                "Camera Icon",
                                tint = Brown500,
                            )
                        }
                    }
                } else {
                    Image(
                        bitmap = crop2.value.asImageBitmap(),
                        contentDescription = "Image",
                        alignment = Alignment.TopCenter,
                        modifier = Modifier
                            .size(0.25.dw)
                            .clip(RoundedCornerShape(percent = 10)),
                        contentScale = ContentScale.FillBounds,
                    )
                }

                if (crop3.value == originalImageBitmap) {
                    Box(Modifier.size(0.25.dw), contentAlignment = Alignment.Center) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawRoundRect(color = Brown500, style = stroke)
                        }
                        IconButton(modifier = Modifier.then(Modifier.size(48.dp)),
                            onClick = {
                                imageCropLauncher3.launch(
                                    CropImageContractOptions(
                                        ImageConversion().convertBitmapToUri(
                                            context,
                                            originalImageBitmap,
                                        ),
                                        CropImageOptions(),
                                    ),
                                )
                            }) {
                            Icon(
                                Icons.Filled.Camera,
                                "Camera Icon",
                                tint = Brown500,
                            )
                        }
                    }
                } else {
                    Image(
                        bitmap = crop3.value.asImageBitmap(),
                        contentDescription = "Image",
                        alignment = Alignment.TopCenter,
                        modifier = Modifier
                            .size(0.25.dw)
                            .clip(RoundedCornerShape(percent = 10)),
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }

            Canvas(Modifier
                .fillMaxWidth()
                .height(1.dp)) {
                drawLine(
                    color = Brown700,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    pathEffect = pathEffect,
                )
            }

            Image(
                bitmap = originalImageBitmap.asImageBitmap(),
                contentDescription = "Image",
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .size(0.32.dh)
                    .clip(RoundedCornerShape(percent = 10))
                    .border(2.dp, Brown500, RoundedCornerShape(percent = 10)),
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}