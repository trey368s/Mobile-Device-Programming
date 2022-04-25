package com.mobiledevelopmentfoodapp


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mobiledevelopmentfoodapp.dto.Customer
import com.mobiledevelopmentfoodapp.dto.Food
import com.mobiledevelopmentfoodapp.ui.theme.FoodAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*


class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModel()
    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setContent {
            val menuItems by viewModel.menuItems.observeAsState(initial = emptyList())
            val onSuccess by viewModel.onSuccess.observeAsState(initial = null)
            val customer by viewModel.customer.observeAsState(initial = null)
            viewModel.fetchRestaurants()
            val restaurants by viewModel.restaurant.observeAsState(initial= emptyList())
            val foods = ArrayList<Food>()
            val context = LocalContext.current
            FoodAppTheme {
                Box (modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.background
                    ) {
                        Header()
                    }
                    MainScreen(viewModel, menuItems, onSuccess, customer)
                    if (onSuccess == true) {
                        ShowToast()
                    }
                }
            }
        }
    }

    private fun signIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private val signInLauncher = registerForActivityResult (
        FirebaseAuthUIActivityResultContract()
    ) {
            res -> this.signInResult(res)
    }

    private fun signInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            firebaseUser = FirebaseAuth.getInstance().currentUser
            firebaseUser?.let {
                val customer = Customer(customerId = it.uid, name = it.displayName ?: "")
                viewModel.customer.value = customer
                viewModel.saveCustomer()
                viewModel.listenToOrders()
            }
        } else {
            Log.e("MainActivity.kt", "Error logging in " + response?.error?.errorCode)

        }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        viewModel.customer.value = null
    }

    private fun getUUIDs(size: Int) = List(size) { UUID.randomUUID().toString() }

    private fun priceString(price: Double): String {
        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
        return currencyFormatter.format(price)
    }

    private fun checkout(orderList: List<Food>, context: Context, customer: Customer?, clearOrder: () -> Unit) {
        if (orderList.isEmpty()) {
            null
        } else {
            if (customer == null) {
                signIn()
            } else {
                viewModel.saveOrder(orderList)
                clearOrder()
            }
        }
    }

    @Composable
    fun MainScreen(viewModel: MainViewModel, menuItems: List<Food>, onSuccess: Boolean?, customer: Customer?) {
        val orderList = remember { listOf<Food>().toMutableStateList() }
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp)
            ) {
                if (menuItems != null) {
                    MenuPanel(
                        items = menuItems,
                        addToCart = { item -> orderList.add(item) }
                    )
                }
                OrderPanel(
                    list = orderList,
                    removeFromCart = { item -> orderList.remove(item) }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                CheckoutButton(orderList = orderList, customer = customer, clearOrder = { orderList.clear() })
            }
        }
    }

    @Composable
    fun Container(children: @Composable() () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 5.dp)
                .background(Color.White)
                .border(
                    width = 0.5.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(5.dp)
                )
        ) {
            children()
        }
    }

    @Composable
    fun Padding(children: @Composable() () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            children()
        }
    }

    @Composable
    fun MenuItem(item: Food, addToCart: (Food) -> Unit) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(text = "${item.name}", fontSize = 22.sp)
                    Text(text = "${item.description}", fontSize = 14.sp)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${priceString(item.price!!)}")
                    IconButton(onClick = { addToCart(item) }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Item to Order")
                    }
                }
            }
        }
    }

    @Composable
    fun MenuPanel(items: List<Food>, addToCart: (Food) -> Unit) {
        Container {
            Text(text = "Menu Items", fontSize = 26.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            MenuList(list = items, addToCart = addToCart)
        }
    }

    @Composable
    fun MenuList(list: List<Food>, addToCart: (Food) -> Unit) {
        if (list.isEmpty()) {
            CircularProgressIndicatorSample()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(
                    items = list,
                    key = { food -> "${food.name}" }
                ) { food ->
                    MenuItem(item = food, addToCart = { addToCart(food) })
                }
            }
        }
    }

    @Composable
    fun OrderItem(item: Food, removeFromCart: (Food) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(text = "${item.name}", fontSize = 20.sp)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "${priceString(item.price!!)}")
                IconButton(onClick = { removeFromCart(item) }) {
                    Icon(Icons.Outlined.Delete, contentDescription = "Remove Item from Order")
                }
            }
        }
    }

    @Composable
    fun OrderPanel(list: List<Food>, removeFromCart: (Food) -> Unit) {
        val uuids = getUUIDs(size = list.size)
        var totalPrice = list.sumOf { food -> food.price ?: 0.0 }
        Column(
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            Container {
                Padding {
                    if (list.isEmpty()) {
                        Text("You have no items in your order", modifier = Modifier.fillMaxWidth())
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Order Total:")
                            Text("${priceString(totalPrice ?: 0.0)}")
                        }
                    }
                    LazyColumn() {
                        itemsIndexed(
                            items = list,
                            key = { index, food ->
                                "${food.name}_${uuids[index]}"
                            }
                        ) { _, food ->
                            OrderItem(
                                item = food,
                                removeFromCart = { removeFromCart(food) }
                            )
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun Header() {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Fast food App", fontSize = 30.sp, textAlign = TextAlign.Center)
            IconButton(onClick = { signOut() }) {
                Icon(Icons.Filled.ExitToApp , contentDescription = "Sign out")
            }
        }
    }

    @Composable
    fun CheckoutButton(orderList: List<Food>, customer: Customer?, clearOrder: () -> Unit) {
        val context = LocalContext.current
        val buttonColor = if (orderList.isEmpty()) Color.Gray else Color.Blue
        val buttonTextColor = if (orderList.isEmpty()) Color.Black else Color.White
        val buttonText = if (orderList.isEmpty()) "Unavailable" else "Place Order"

        FloatingActionButton(
            onClick = {
                checkout(orderList, context, customer, clearOrder)
            },
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            backgroundColor = buttonColor,
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (customer != null) {
                    Text(text = buttonText, color = buttonTextColor, textAlign = TextAlign.Center)
                } else {
                    Text(text = "Sign in to order", color = buttonTextColor, textAlign = TextAlign.Center)
                }
            }
        }
    }

    @Composable
    fun ShowToast(msg: String = "Your order was success fully entered and will be ready in 20 minutes") {
        val context = LocalContext.current
        Box(modifier = Modifier.fillMaxSize()) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            viewModel.onSuccess.value = null
        }
    }

    @Composable
    fun CircularProgressIndicatorSample() {
        val progressValue = 0.95f
        val infiniteTransition = rememberInfiniteTransition()

        val progressAnimationValue by infiniteTransition.animateFloat(
            initialValue = 0.0f,
            targetValue = progressValue,animationSpec = infiniteRepeatable(animation = tween(900)))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator(progress = progressAnimationValue)
        }
    }

}

