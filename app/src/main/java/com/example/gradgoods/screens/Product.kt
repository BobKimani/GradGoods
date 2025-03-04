package com.example.gradgoods.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.gradgoods.model.Product
import com.example.gradgoods.nav.Screen
import com.example.gradgoods.screens.CartScreen
import com.example.gradgoods.screens.HomeScreen
import com.example.gradgoods.model.CartViewModel

@Composable
fun ProductScreen(navController: NavController, product: Product,cartViewModel: CartViewModel) {
    var quantity by remember { mutableStateOf(1) }

    val GradGoodsPurple = Color(0xFF6A1B9A)
    val DarkPurple = Color(0xFFF5E5F3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        // Top Bar with Back and Favorite buttons
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }
            Spacer(modifier = Modifier.weight(2f))

            IconButton(onClick = { /* Handle favorite action */ }) {
                Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorite", tint = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        // Product Image
        Image(
            painter = rememberAsyncImagePainter(product.imageUrl),
            contentDescription = product.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Product Details
        Text(text = product.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = product.description, fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))


        // Quantity Selector
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Quantity :",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            IconButton(onClick = { if (quantity > 1) quantity-- }) {
                Text("-", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Text(text = "$quantity", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
            IconButton(onClick = { quantity++ }) {
                Text("+", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Add to Cart Button
        Button(
            onClick = {
                Log.d("CartDebug", "Selected Quantity before adding to cart: $quantity")

                cartViewModel.addToCart(product, quantity)
                navController.navigate(Screen.Cart.route)
                      },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start= 40.dp, end = 40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D336B)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Add to Cart", color = Color.White, fontSize = 18.sp)
        }
    }
}