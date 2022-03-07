
package com.mobiledevelopmentfoodapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevelopmentfoodapp.dto.Food
import com.mobiledevelopmentfoodapp.service.RestaurantService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var restaurant : MutableLiveData<List<Food>> = MutableLiveData<List<Food>>()
    var RestaurantService : RestaurantService = RestaurantService()

    // sets innerRestaurant value to list of restaurants
    fun fetchRestaurants() {
        viewModelScope.launch {
            var innerRestaurant = RestaurantService.fetchFoods()
            restaurant.postValue(innerRestaurant)
        }
    }
}