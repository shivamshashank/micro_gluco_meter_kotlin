package com.example.microglucometer.presentation.screens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.microglucometer.presentation.theme.Brown700
import com.example.microglucometer.utils.ImageConversion
import com.example.microglucometer.view_model.RecordViewModel
import com.slaviboy.composeunits.dw

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecordsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Records") },
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
        }
    ) {
        RecordsScreenBody()
    }
}

@Composable
fun RecordsScreenBody() {
    val context = LocalContext.current

    val mRecordViewModel = RecordViewModel(context.applicationContext as Application)

    val recordList = mRecordViewModel.readAllData.observeAsState(listOf()).value

    if (recordList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = "No Record Found",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
        ) {
            items(recordList) { record ->
                Card(
                    elevation = 6.dp,
                    border = BorderStroke(1.dp, Brown700),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        DetailRow(label = "Name : ", value = record.name)
                        DetailRow(label = "Gender : ", value = record.gender)
                        DetailRow(label = "Age : ", value = record.age)
                        DetailRow(label = "Phone Number : ", value = record.phoneNumber)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            ImageText(
                                regionOfInterest = record.region_of_interest1,
                                concentration = record.concentration1,
                            )
                            ImageText(
                                regionOfInterest = record.region_of_interest2,
                                concentration = record.concentration2,
                            )
                            ImageText(
                                regionOfInterest = record.region_of_interest3,
                                concentration = record.concentration3,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Text(text = label, color = Color.Black)
        Text(text = value, color = Brown700)
    }
}

@Composable
private fun ImageText(regionOfInterest: String, concentration: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            bitmap = ImageConversion().getBitmapImage(regionOfInterest)
                .asImageBitmap(),
            contentDescription = "Image",
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .size(0.24.dw)
                .clip(RoundedCornerShape(percent = 10)),
            contentScale = ContentScale.FillBounds,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = concentration.substring(
                1,
                concentration.length - 1
            ) + " Mm",
            style = TextStyle(fontSize = 20.sp),
        )
    }
}