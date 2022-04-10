
package com.mobiledevelopmentfoodapp

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevelopmentfoodapp.dto.Food
import com.mobiledevelopmentfoodapp.service.RestaurantService
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MainViewModel : ViewModel() {

    var restaurant : MutableLiveData<List<Food>> = MutableLiveData<List<Food>>()
    var RestaurantService : RestaurantService = RestaurantService()

    private lateinit var firestore : FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun fetchRestaurants() {
        viewModelScope.launch {
            var innerRestaurant = RestaurantService.fetchFoods()
            restaurant.postValue(innerRestaurant)
            var foodList = firestore.collection("FoodItems")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }
    }
}