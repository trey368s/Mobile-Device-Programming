package com.mobiledevelopmentfoodapp.dto

//import androidx.room.Entity
//import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Order(
    var customerId: String,
     var products: List<Food>,
     var totalPrice: Double,
     @SerializedName("id") var orderId:String = ""
)