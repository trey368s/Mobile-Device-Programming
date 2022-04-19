package com.mobiledevelopmentfoodapp.dto

import com.google.gson.annotations.SerializedName

//import androidx.room.Entity
//import androidx.room.PrimaryKey

data class Food(
    var name: String? = null,
    var description: String? = null,
    var price: Double? = null,
    @SerializedName("id") var productId: String = "") {
}
