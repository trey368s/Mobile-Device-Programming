
package com.mobiledevelopmentfoodapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevelopmentfoodapp.dto.Food
import com.mobiledevelopmentfoodapp.service.IRestaurantService
import com.mobiledevelopmentfoodapp.service.RestaurantService
import kotlinx.coroutines.launch

class MainViewModel (var RestaurantService : IRestaurantService = RestaurantService()): ViewModel() {

    var restaurant : MutableLiveData<List<Food>> = MutableLiveData<List<Food>>()

    private lateinit var firestore: FirebaseFirestore

    fun fetchRestaurants() {
        viewModelScope.launch {
            var innerRestaurant = RestaurantService.fetchFoods()
            restaurant.postValue(innerRestaurant)
        }
    }
}