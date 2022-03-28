package com.mobiledevelopmentfoodapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobiledevelopmentfoodapp.ui.theme.FoodAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodAppTheme {
                Box (modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.background
                    ) {
                        Header("Android")
                    }
                    ButtonBar()
                    Menu()
                }
            }
        }
    }
}

@Composable
fun Header(name: String) {
    Text(text = "Fastfood Restaurant", fontSize = 30.sp, textAlign = TextAlign.Center)
}

@Composable
fun ButtonBar() {
    Row(modifier = Modifier.padding(all = 2.dp)) {
    }

    val context = LocalContext.current
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Button(
            onClick = {
                Toast.makeText(context, "TAKES USER TO CART PAGE", Toast.LENGTH_LONG).show()
            },
            modifier = Modifier.padding(all = 55.dp),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.medium,
        ) {
            Text(text = "View Cart", color = Color.White)
        }
    }
}

@Composable
fun Menu() {
    val items = listOf("A", "B", "C", "D", "E", "F")
    var menuName : String by remember { mutableStateOf("Menu Categories") }
    var expanded by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            Modifier
                .padding(100.dp)
                .clickable {
                    expanded = !expanded
                }
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text= menuName, fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
            DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}) {
                items.forEachIndexed {
                        index, s -> DropdownMenuItem(onClick = {
                    expanded = false

                }) {
                    Text(text = s)
                }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodAppTheme {
        Header("Android")
    }
}