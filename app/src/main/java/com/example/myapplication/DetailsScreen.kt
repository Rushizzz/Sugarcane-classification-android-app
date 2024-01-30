package com.example.myapplication

import android.telecom.Call.Details
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun DetailsScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .clickable {
                navController.navigate("Home"){
                    launchSingleTop=true
                }
            }
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Detail Screen ")
    }
}