
package com.mobiledevelopmentfoodapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.mobiledevelopmentfoodapp.dto.Customer
import com.mobiledevelopmentfoodapp.dto.Food
import com.mobiledevelopmentfoodapp.dto.Order
import com.mobiledevelopmentfoodapp.service.IRestaurantService
import com.mobiledevelopmentfoodapp.service.RestaurantService
import kotlinx.coroutines.launch

class MainViewModel (var RestaurantService : IRestaurantService = RestaurantService()): ViewModel() {

    var customer : Customer? = null;
    var restaurant : MutableLiveData<List<Food>> = MutableLiveData<List<Food>>()
    var restaurantService : RestaurantService = RestaurantService()

   var menuItems: MutableLiveData<List<Food>> = MutableLiveData<List<Food>>()

    private var firestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        firestore.firestoreSettings= FirebaseFirestoreSettings.Builder().build()
        listenToFood()
    }

    private fun listenToFood() {
        firestore.collection("MenuItems").addSnapshotListener {
                snapshot, e ->
                if (e!=null){
                    Log.w("Listen failed",e)
                    return@addSnapshotListener
                }
            snapshot?.let {
                val allMenuItems = ArrayList<Food>()
                val documents = snapshot.documents
                documents.forEach{
                    val food = it.toObject(Food::class.java)
                    food?.let { allMenuItems.add(it) }
                }
                menuItems.value = allMenuItems
            }
        }
    }

    fun listenToOrders() {
        customer?.let {
                customer ->
            firestore.collection("users").document(customer.customerId).collection("Order").addSnapshotListener {
                    snapshot, e ->
                // handle the error if there is one, and then return
                if (e != null) {
                    Log.w("Listen failed", e)
                    return@addSnapshotListener
                }
                // if we reached this point , there was not an error
                snapshot?.let {
                    val allOrders = ArrayList<Order>()
                    val documents = snapshot.documents
                    documents.forEach{
                        val order = it.toObject(Order::class.java)
                        order?.let { allOrders.add(it) }
                    }
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
        food.productId = document.id
        val handle = document.set(food)
        handle.addOnSuccessListener { Log.d("Firebase","Document saved") }
        handle.addOnFailureListener { Log.e("Firebase","Save failed $it") }
    }

    fun saveOrder(orderList: List<Food>){
        val document = firestore.collection("Food").document()
//        food.productId = document.id
//        val handle = document.set(food)
//        handle.addOnSuccessListener { Log.d("Firebase","Document saved") }
//        handle.addOnFailureListener { Log.e("Firebase","Save failed $it") }
    }
}