
package com.mobiledevelopmentfoodapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.mobiledevelopmentfoodapp.dto.Food
import com.mobiledevelopmentfoodapp.dto.Order
import com.mobiledevelopmentfoodapp.service.IRestaurantService
import com.mobiledevelopmentfoodapp.service.RestaurantService
import kotlinx.coroutines.launch


class MainViewModel (var RestaurantService : IRestaurantService = RestaurantService()): ViewModel() {

    var restaurant : MutableLiveData<List<Food>> = MutableLiveData<List<Food>>()

    private lateinit var firestore : FirebaseFirestore
    init {
        firestore=FirebaseFirestore.getInstance()
        firestore.firestoreSettings= FirebaseFirestoreSettings.Builder().build()
    }

    fun fetchRestaurants() {
        viewModelScope.launch {
            var innerRestaurant = RestaurantService.fetchFoods()
            restaurant.postValue(innerRestaurant)
        }
    }
    fun save(order: Order){
        val document = firestore.collection("Order").document()
        order.orderId = document.id
        val handle = document.set(order)
        handle.addOnSuccessListener { Log.d("Firebase","Document saved") }
        handle.addOnFailureListener { Log.e("Firebase","Save failed $it") }
    }
}