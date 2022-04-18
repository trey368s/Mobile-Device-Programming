package com.mobiledevelopmentfoodapp.service

import com.mobiledevelopmentfoodapp.dto.Food

interface IRestaurantService {
    suspend fun fetchFoods(): List<Food>?
}