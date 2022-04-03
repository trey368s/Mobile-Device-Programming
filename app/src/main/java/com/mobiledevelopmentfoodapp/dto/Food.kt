package com.mobiledevelopmentfoodapp.dto

//import androidx.room.Entity
//import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Food(var name: String,
                var description: String,
                var price: Double,
                @SerializedName("productId") var productId:Int = 0) {
    override fun toString(): String {
        return name
    }
}
