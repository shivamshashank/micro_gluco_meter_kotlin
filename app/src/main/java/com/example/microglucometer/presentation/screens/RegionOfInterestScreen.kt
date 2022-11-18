package com.example.microglucometer.presentation.screens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.microglucometer.models.User
import com.example.microglucometer.presentation.state.GetConcentrationState
import com.example.microglucometer.presentation.theme.Brown500
import com.example.microglucometer.utils.ImageConversion
import com.example.microglucometer.utils.Screen
import com.example.microglucometer.view_model.GetConcentrationViewModel
import com.example.microglucometer.view_model.RecordViewModel
import com.slaviboy.composeunits.dh

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegionOfInterestScreen(
    navController: NavController,
    user: User,
    imageMap: HashMap<String, String>,
) {

    val concentrationViewModel = hiltViewModel<GetConcentrationViewModel>()
    val recordViewModel = RecordViewModel(LocalContext.current.applicationContext as Application)

    concentrationViewModel.getConcentration(imageMap)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Region Of Interest") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.RecordsScreen.route) {
                                popUpTo(Screen.RegistrationScreen.route)
                            }
                        },
                    ) {
                        Icon(Icons.Filled.Folder, "Records")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Brown500,
                onClick = {
                    navController.navigate(Screen.RegistrationScreen.route) {
                        popUpTo(0)
                    }
                },
                content = {
                    Icon(
                        Icons.Filled.Add,
                        "Add New Record",
                        tint = Color.White,
                    )
                }
            )
        },
    ) {
        val getConcentrationState by concentrationViewModel.getConcentrationState.collectAsState()

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
                    (getConcentrationState as GetConcentrationState.Success).concentrationModel

                recordViewModel.addRecord(user, concentration)

                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    ImageAndText(
                        regionOfInterest = concentration.region_of_interest1,
                        concentration = concentration.concentration1,
                    )

                    ImageAndText(
                        regionOfInterest = concentration.region_of_interest2,
                        concentration = concentration.concentration2,
                    )

                    ImageAndText(
                        regionOfInterest = concentration.region_of_interest3,
                        concentration = concentration.concentration3,
                    )
                }
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

@Composable
private fun ImageAndText(regionOfInterest: String, concentration: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(
            bitmap = ImageConversion().getBitmapImage(regionOfInterest).asImageBitmap(),
            contentDescription = "Image",
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .size(0.24.dh)
                .clip(RoundedCornerShape(percent = 10))
                .border(2.dp, Brown500, RoundedCornerShape(percent = 10)),
            contentScale = ContentScale.FillBounds,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = concentration.substring(1, concentration.length - 1) + " Mm",
                style = TextStyle(fontSize = 28.sp),
            )
            Text(
                text = "Prediction",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.Underline,
                ),
            )
        }
    }
}