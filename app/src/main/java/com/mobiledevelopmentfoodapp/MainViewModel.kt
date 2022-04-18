
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


    private var restaurant : MutableLiveData<List<Food>> = MutableLiveData<List<Food>>()
    var restaurantService : RestaurantService = RestaurantService()

    private lateinit var firestore : FirebaseFirestore
    init {
        firestore=FirebaseFirestore.getInstance()
        firestore.firestoreSettings= FirebaseFirestoreSettings.Builder().build()
        listentoFood()
    }

    private fun listentoFood() {
        firestore.collection("Food").addSnapshotListener {
                snapshot, e ->
                if (e!=null){
                    Log.w("Listen failed",e)
                    return@addSnapshotListener
                }
            snapshot?.let {
                val allFoods = ArrayList<Food>()
                val documents = snapshot.documents
                documents.forEach{
                    val food = it.toObject(Food::class.java)
                    food?.let { allFoods.add(it) }
                }
            }
        }
    }

    fun fetchRestaurants() {
        viewModelScope.launch {
            var innerRestaurant = restaurantService.fetchFoods()
            restaurant.postValue(innerRestaurant)
        }
    }
    fun save(food: Food){
        val document = firestore.collection("Food").document()
        food.Id = document.id
        val handle = document.set(food)
        handle.addOnSuccessListener { Log.d("Firebase","Document saved") }
        handle.addOnFailureListener { Log.e("Firebase","Save failed $it") }
    }
}