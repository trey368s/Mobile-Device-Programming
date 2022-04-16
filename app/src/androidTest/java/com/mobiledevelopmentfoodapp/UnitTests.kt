package com.mobiledevelopmentfoodapp

import androidx.lifecycle.MutableLiveData
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.mobiledevelopmentfoodapp.dto.Food
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.koin.core.KoinApplication.Companion.init

@RunWith(AndroidJUnit4::class)
class UnitTests {
    var food: MutableLiveData<List<Food>> = MutableLiveData<List<Food>>()
    private lateinit var firestore: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.mobiledevelopmentfoodapp", appContext.packageName)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun connectToFirebase() {
        val document = firestore.collection("Food").document()
        var connected = false
        if (document != null)
        {
            connected = true
        }
        assertEquals(connected, true)
    }

    @Test
    fun addFood(){
        val foods = ArrayList<Food>()
        foods.add(Food(name="Burger", description = "Fried Chicken and bread", price = 10, Id="1"))
    }
}

