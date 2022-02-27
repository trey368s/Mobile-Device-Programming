package com.mobiledevelopmentfoodapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mobiledevelopmentfoodapp.ui.theme.FoodAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodAppTheme {
                Box (modifier = Modifier .fillMaxSize() .background(Color.LightGray)) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.background
                    ) {
                        Header("Android")
                    }
                }
            }
        }
    }
}

@Composable
fun Header(name: String) {
    Text(text = "Fastfood Restaurant", fontSize = 30.sp, textAlign = TextAlign.Center)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodAppTheme {
        Header("Android")
    }
}