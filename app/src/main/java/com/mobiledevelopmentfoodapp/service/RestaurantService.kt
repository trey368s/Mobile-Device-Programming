package com.mobiledevelopmentfoodapp.service

import com.mobiledevelopmentfoodapp.RetrofitClientInstance
import com.mobiledevelopmentfoodapp.dao.IFoodDAO
import com.mobiledevelopmentfoodapp.dto.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class RestaurantService {
    suspend fun fetchFoods(): List<Food>?{
        return withContext(Dispatchers.IO) {
            val service = RetrofitClientInstance.retrofitInstance?.create(IFoodDAO::class.java)
            val foods = async { service?.getAllFoods() }
            return@withContext foods.await()?.awaitResponse()?.body()
        }
    }
}