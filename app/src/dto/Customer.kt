package app.src.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Customer(var name: String, var phone: String, @PrimaryKey @SerializedName("id") var customerId:Int = 0 ) {
    override fun toString(): String {
        return common
    }
}