package com.mobiledevelopmentfoodapp.dto

import com.google.gson.annotations.SerializedName

data class Customer(
    internal var name: String,
    internal var phone: String,
    @SerializedName("id")internal var customerId: Int = 0,
) {
    override fun toString(): String {
        return name
    }
}