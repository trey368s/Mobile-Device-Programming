package app.src.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Item(var name: String, var description: String, var price: double, @PrimaryKey @SerializedName("id") var itemId:Int = 0 ) {
    override fun toString(): String {
        return common
    }
}