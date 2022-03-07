
package com.mobiledevelopmentfoodapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevelopmentfoodapp.dto.Food
import com.mobiledevelopmentfoodapp.service.RestaurantService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var restaurant : MutableLiveData<List<Food>> = MutableLiveData<List<Food>>()
    var restaurantService : RestaurantService = RestaurantService()

    fun fetchRestaurants() {
        viewModelScope.launch {
            var innerRestaurant = restaurantService.fetchFoods()
            restaurant.postValue(innerRestaurant)
        }
    }
}