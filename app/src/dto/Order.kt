package app.src.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Product(var customerId: Intnt, var products: ArrayList<Product>, var totalPrice: double, @PrimaryKey @SerializedName("id") var orderId:Int = 0 ) {
    override fun toString(): String {
        return common
    }
}