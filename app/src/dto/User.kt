package app.src.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(var name: String, var phone: String, @PrimaryKey @SerializedName("id") var userId:Int = 0 ) {
    override fun toString(): String {
        return common
    }
}