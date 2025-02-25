package com.example.gradgoods.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlin.math.max
import com.example.gradgoods.model.CartViewModel
import com.example.gradgoods.model.Product
import com.example.gradgoods.nav.BottomNavBar

@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel) {
    val GradGoodsPurple = Color(0xFF6A1B9A)
    val LightPurple = Color(0xFFF5E5F3)
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalPrice = cartViewModel.getTotalPrice()

    val scrollState = rememberLazyListState()
    val scrollOffset by remember { derivedStateOf { scrollState.firstVisibleItemScrollOffset.toFloat() } }

    // Make sure the header does not shrink too much
    val collapseFactor = max(0.7f, 1f - (scrollOffset / 400f)) // Adjusted for better visibility

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightPurple)
    ) {
        Column {
            // Collapsing Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((120 * collapseFactor).dp) // Increased max height for better visibility
                    .padding(top = 30.dp, start = 20.dp, end = 20.dp), // Added more top padding
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = GradGoodsPurple)
                    }
                    Text(
                        text = "Cart",
                        fontSize = (32 * collapseFactor).sp, // Increased font size for better visibility
                        fontWeight = FontWeight.Bold,
                        color = GradGoodsPurple
                    )
                    IconButton(onClick = { cartViewModel.clearCart() }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Clear Cart", tint = GradGoodsPurple)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Cart Items List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 140.dp), // Added bottom padding to prevent navigation bar overlap
                state = scrollState
            ) {
                items(cartItems) { product ->
                    CartItem(product, cartViewModel)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            // Total & Checkout Section
                item {
                    Text(text = "Total Price", fontSize = 16.sp, color = Color.Gray)
                    Text(
                        text = "Ksh.$totalPrice",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { /* Navigate to Checkout */ },
                        colors = ButtonDefaults.buttonColors(containerColor = GradGoodsPurple),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Check Out", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }

        // Bottom Navigation Bar (remains at the bottom)
        BottomNavBar(
            selectedRoute = "cart",
            onItemSelected = { navController.navigate(it) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 5.dp) // Added slight padding to avoid cutting off
        )
    }
}

@Composable
fun CartItem(product: Product, cartViewModel: CartViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(15.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = "Product Image",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.name, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
            Text(text = "Ksh.${product.price}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { cartViewModel.decreaseQuantity(product) }) {
                Text("-", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = GradGoodsPurple)
            }
            Text(text = "${product.quantity}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = { cartViewModel.increaseQuantity(product) }) {
                Text("+", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = GradGoodsPurple)
            }
        }
    }
}