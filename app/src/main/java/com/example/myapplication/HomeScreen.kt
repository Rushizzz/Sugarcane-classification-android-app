package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun HomeScreen(navcontroller:NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hii Welcome to AgriSakha",
            fontSize = 64.sp,
            lineHeight = 60.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(25.dp)
        )
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(350.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(color = Color(android.graphics.Color.parseColor("#2f2f33")))


        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)


            ) {
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 10.dp)

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.upload),
                        contentDescription = "upload image",

                        modifier = Modifier
                            .padding(20.dp)
                            .size(48.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.diagnosis),
                        contentDescription = "see diagnosis",
                        modifier = Modifier
                            .padding(20.dp)
                            .size(48.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.nutrition),
                        contentDescription = "nutrition",
                        modifier = Modifier
                            .padding(20.dp)
                            .size(48.dp)

                    )
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .height(48.dp)
                ) {
                    Text(
                        "Select image",
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "homescreenPreview"
)
@Composable
fun homeScreenPreview() {
    MyApplicationTheme {
        val controller = rememberNavController()
        HomeScreen(navcontroller = controller)
    }
}