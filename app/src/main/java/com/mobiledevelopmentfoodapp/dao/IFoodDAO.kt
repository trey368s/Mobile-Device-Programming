package com.mobiledevelopmentfoodapp.dao

import com.mobiledevelopmentfoodapp.dto.Food
import retrofit2.Call
import retrofit2.http.GET
// Get list of data from source
interface IFoodDAO {
    @GET("https://pkgstore.datahub.io/core/country-list/data_json/data/8c458f2d15d9f2119654b29ede6e45b8/data_json.json")
    fun getAllFoods(): Call<ArrayList<Food>>
}