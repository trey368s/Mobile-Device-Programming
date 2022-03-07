package com.mobiledevelopmentfoodapp.dto

//import androidx.room.Entity
//import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Customer(
    var name: String,
    var phone: String,
    @SerializedName("id")
    var customerId:Int = 0 ,
) {
    override fun toString(): String {
        return name
    }
}