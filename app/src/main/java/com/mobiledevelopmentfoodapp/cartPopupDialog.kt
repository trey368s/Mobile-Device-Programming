package com.mobiledevelopmentfoodapp

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable

fun cartPopupDialog(myCart: List<String>){

    val openDialog = remember{mutableStateOf(true)}

    if(openDialog.value){

        Dialog(onDismissRequest = {openDialog.value = false}){
            Surface(
                modifier = androidx.compose.ui.Modifier
                    .width(300.dp)
                    .height(500.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ){
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        //Header to dialog box
                        Text(
                            modifier = Modifier
                                .weight(6f)
                                .padding(10.dp),
                            text = "Your Cart",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                        //cancel button to dialog box
                        IconButton(
                            onClick = {
                                openDialog.value = false
                            },
                            modifier = Modifier
                                .weight(1.5f)
                                //.height(60.dp)
                                .padding(10.dp)
                                .background(color = Color.Red, shape = RoundedCornerShape(5.dp))


                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Symbol"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    //place to load order list into
                    myCart.forEach { position ->
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Text(
                                modifier = Modifier
                                    .weight(6f)
                                    .padding(10.dp),
                                text = position,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp)
                        }

                    }

                    Spacer(modifier = Modifier.padding(10.dp))
                    //button to delete the room
                    Button(
                        onClick = {

                            openDialog.value = false
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(60.dp)
                            .padding(10.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(Color.Green)
                    ) {
                        Text(
                            text = "Submit Order",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    //button to delete the room
                    Button(
                        onClick = {

                            openDialog.value = false
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(60.dp)
                            .padding(10.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Text(
                            text = "Delete Order",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }
        }
    }

}