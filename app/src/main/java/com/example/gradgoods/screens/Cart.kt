package com.example.gradgoods.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gradgoods.model.CartViewModel
import com.example.gradgoods.model.Product
import com.example.gradgoods.nav.BottomNavBar

@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel) {
    val GradGoodsPurple = Color(0xFF6A1B9A)
    val LightPurple = Color(0xFFF5E5F3)
    val cartItems by cartViewModel.cartItems
    val totalPrice = cartViewModel.getTotalPrice()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightPurple)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightPurple)
                .padding(top = 100.dp)
        ) {
            Text(
                text = "Shopping Cart",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = GradGoodsPurple,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (cartItems.isEmpty()) {
                Text(
                    text = "Your cart is empty",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { product ->
                        CartItem(product, cartViewModel)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Total: Ksh.$totalPrice",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { cartViewModel.clearCart() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Clear Cart", color = Color.White, fontSize = 16.sp)
                    }

                    Button(
                        onClick = { /* Navigate to Checkout */ },
                        colors = ButtonDefaults.buttonColors(containerColor = GradGoodsPurple),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Proceed to Checkout", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }

        BottomNavBar(
            selectedRoute = "cart",
            onItemSelected = { navController.navigate(it) },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun CartItem(product: Product, cartViewModel: CartViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "Ksh.${product.price}", fontSize = 16.sp, color = Color.Black)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { cartViewModel.decreaseQuantity(product) }) {
                Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Text(text = "${product.quantity}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = { cartViewModel.increaseQuantity(product) }) {
                Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }

        IconButton(onClick = { cartViewModel.removeFromCart(product) }) {
            Text("❌", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Red)
        }
    }
}