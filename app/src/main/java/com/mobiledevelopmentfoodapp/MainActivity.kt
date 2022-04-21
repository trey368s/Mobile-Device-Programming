package com.mobiledevelopmentfoodapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.auth.User
import com.mobiledevelopmentfoodapp.dto.Customer
import com.mobiledevelopmentfoodapp.dto.Food
import com.mobiledevelopmentfoodapp.ui.theme.FoodAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setContent {
            val menuItems by viewModel.menuItems.observeAsState(initial = emptyList())
            viewModel.fetchRestaurants()
            val restaurants by viewModel.restaurant.observeAsState(initial= emptyList())
            val foods = ArrayList<Food>()
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
                    MainScreen(viewModel, menuItems)
                }
            }
        }
    }


    private fun getMenuItems(): List<Food> {
        val menuItems: MutableList<Food> = listOf<Food>().toMutableList()
        val burger = Food(name = "Hamburger", description = "One tasty burger", price = 8.0, productId = "001")
        val pizza = Food(name = "Pizza", description = "Large Pepperoni pizza", price = 18.0, productId = "002")
        val soda = Food(name = "Soda", description = "Tasty beverage", price = 2.0, productId = "003")
        menuItems.add(burger)
        menuItems.add(pizza)
        menuItems.add(soda)
        return menuItems.toList()
    }

    private fun getUUIDs(size: Int) = List(size) { UUID.randomUUID().toString() }

    @Composable
    fun MainScreen(viewModel: MainViewModel, menuItems: List<Food>) {
        val orderList = remember { listOf<Food>().toMutableStateList() }
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
            CheckoutButton(orderList = orderList)
        }

    }

    @Composable
    fun Container(children: @Composable() () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp)
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
        val currencySymbol = '$'
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
                    Text(text = "$currencySymbol${item.price}")
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
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
        ) {
            items(
                items = list,
                key = { food -> "${food.name}" }
            ) {
                food -> MenuItem(item = food, addToCart = { addToCart(food) })
            }
        }
    }

    @Composable
    fun OrderItem(item: Food, removeFromCart: (Food) -> Unit) {
        val currencySymbol = '$'
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
                Text(text = "$currencySymbol${item.price}")
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
        Container {
            Padding {
                Text(
                    if (list.isEmpty()) "You have no items in your order" else "Order Details",
                    modifier = Modifier.fillMaxWidth()
                )
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Order Total:")
                    Text("\$${totalPrice ?: 0.0}")
                }
            }
        }
    }

    @Composable
    fun Header() {
        Text(text = "Fast food App", fontSize = 30.sp, textAlign = TextAlign.Center)
    }

    @Composable
    fun CheckoutButton(orderList: List<Food>) {
        val context = LocalContext.current
        var customer : Customer? = viewModel.customer

        FloatingActionButton(
            onClick = {
                if (orderList.isEmpty()) {
                    null
                } else {
                    Log.i("order", orderList.toString())
                    Toast.makeText(context, "User payment", Toast.LENGTH_LONG).show()
                }
            },
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            backgroundColor = Color.Blue,
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                if (customer != null) {
                    Text(text = "Checkout", color = Color.White, textAlign = TextAlign.Center)
                } else {
                    Text(text = "Sign in to order", color = Color.White, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

