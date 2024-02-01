package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {

                val controller = rememberNavController()
                NavHost(navController =controller, startDestination = "Home"){

                    composable("Home"){
                            HomeScreen()
                    }

                    composable("Detail"){
                        DetailsScreen(controller)
                    }

                    composable("Prediction"){
                        PredictionScreen(controller)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    LazyColumn(){
        items(5){
            Icon(imageVector = Icons.Default.Add, contentDescription =null )
        }
    }


}

@Preview(
    showBackground = true,
    name = "my preview"
)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Rushikesh")
    }
}

