package com.mobiledevelopmentfoodapp.dto

import com.google.gson.annotations.SerializedName

data class Order(
    internal var customerId: Int,
    internal var name: String,
    internal var products: ArrayList<Food>,
    internal var totalPrice: Double,
    @SerializedName("id")internal var orderId: Int = 0,
) {
    override fun toString(): String {
        return name
    }
}