package com.mobiledevelopmentfoodapp.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import app.plantdiary.dao.ILocalFoodDAO
import com.mobiledevelopmentfoodapp.dto.Food

@Database(entities= [Food::class], version = 1)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun localFoodDAO() : ILocalFoodDAO
}