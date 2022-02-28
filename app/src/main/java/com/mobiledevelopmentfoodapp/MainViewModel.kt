
package com.mobiledevelopmentfoodapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var restaurant : MutableLiveData<List<Restaurant>> = MutableLiveData<List<Restaurant>>()
    var RestaurantService : RestaurantService = RestaurantService()

    fun fetchRestaurants() {
        viewModelScope.launch {
            var innerRestaurant = RestaurantService.fetchRestaurant()
            restaurants.postValue(innerRestaurant)
        }
    }
}