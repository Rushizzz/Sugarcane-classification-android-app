package com.example.myapplication

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ml.Model
import com.example.myapplication.ui.theme.MyApplicationTheme
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer



@Composable
fun HomeScreen(controller: NavController, labels: List<String>) {
    Box (modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "backgorund image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        var showDialog by remember { mutableStateOf(false) }
        var predScreen by remember {
            mutableStateOf(true)
        }

        var disease by remember {
            mutableStateOf("no disease deteceted")
        }

        val Context = LocalContext.current
        val img: Bitmap = BitmapFactory.decodeResource(Resources.getSystem(), android.R.drawable.ic_menu_report_image)
        val bitmap = remember {
            mutableStateOf(img)
        }

        var imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224,224,ResizeOp.ResizeMethod.BILINEAR))
            .build()



        fun prediction(bitmap: Bitmap) : String {

            var tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)

            tensorImage = imageProcessor.process(tensorImage)

            val model = Model.newInstance(Context)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(tensorImage.buffer)


            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            var maxIdx = 0
            outputFeature0.forEachIndexed{index, fl ->
                if (outputFeature0[maxIdx] < fl) {
                    maxIdx = index
                }
            }

            var finalPrediction = labels[maxIdx]
            model.close()
            return finalPrediction
        }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) {
            if (it != null) {
                bitmap.value = it
                val temp = prediction(bitmap.value)
                disease = temp.toString()
                predScreen = false
            }
        }

        val launchImage = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(Context.contentResolver, it)
                val temp = prediction(bitmap.value)
                disease = temp.toString()
                predScreen = false
            }
            else{
                val source = it?.let { it1 ->
                    ImageDecoder.createSource(Context.contentResolver, it1)

                }
                bitmap.value = source?.let { it1 -> 
                    ImageDecoder.decodeBitmap((it1))
                }!!
                val temp = prediction(bitmap.value)
                disease = temp.toString()
                predScreen = false
            }
        }



        if (predScreen) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                ,

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
                            onClick = { showDialog = true },
                            colors = ButtonDefaults.buttonColors(Color.White),
                            modifier = Modifier
                                .width(300.dp)
                                .height(48.dp)

                        ) {
                            Text(
                                "Select image",
                                color = Color.Black,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }
        else{

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)

            ) {
                Column (

                    modifier = Modifier
//                        .padding(top = 100.dp, start = 20.dp)
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(android.graphics.Color.parseColor("#2f2f33")))

                ) {
                    Text (
                        text = "Detected Disease",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 10.dp)
                    )

                    Text (
                        text= disease,
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 30.dp)
                    )
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 300.dp)
                        .height(64.dp)
                        .width(300.dp)
                ) {
                    Text(
                        text = "Go back",
                        fontSize = 28.sp
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
        ) {
            if (showDialog) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(300.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                ) {
                    Column (
                        modifier = Modifier.padding(start = 60.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_add_a_photo_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    launcher.launch()
                                    showDialog = false
                                }
                            )
                        Text("Camera", color = Color.Black)
                    }
                    Spacer(modifier = Modifier.padding(30.dp))
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_image_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    launchImage.launch("image/*")
                                    showDialog = false
                                }
                        )
                        Text(text = "Gallary",
                             color = Color.Black)
                    }
                    Column (
                        modifier = Modifier
                            .padding(start = 30.dp, bottom = 60.dp)
                    ) {
                        Text(
                            text = "X",
                            color = Color.Black,
                            modifier = Modifier
                                .clickable { showDialog = false }
                        )
                    }
                }
            }
        }
    }

}


