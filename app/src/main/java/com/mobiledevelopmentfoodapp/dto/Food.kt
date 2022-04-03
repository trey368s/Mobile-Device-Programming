package com.mobiledevelopmentfoodapp.dto

import com.google.gson.annotations.SerializedName

data class Food(
    internal var name: String,
    internal var description: String,
    internal var price: Double,
    @SerializedName("id")internal var productId: Int = 0,
) {
    override fun toString(): String {
        return name
    }
}
