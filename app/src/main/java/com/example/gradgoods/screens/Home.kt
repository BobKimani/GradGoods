package com.example.gradgoods.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter // CHANGED: Import Coil
import com.example.gradgoods.model.CartViewModel
import com.example.gradgoods.nav.BottomNavBar
import com.example.gradgoods.model.Product
import com.example.gradgoods.model.ProductsViewModel
import com.example.gradgoods.nav.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    productsViewModel: ProductsViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(bottom = 56.dp), // Leaves space for BottomNavBar
            contentPadding = PaddingValues(bottom = 80.dp) // Prevents last item from being hidden
        ) {
            item { TopBar(navController,cartViewModel) }
            item { CashbackBanner() }
            item {
                CategoryList(
                    categories = listOf("Clothing", "Gadgets", "Fashion", "Food", "Sports", "Books"),
                    onCategoryClick = { categories ->
                        Log.d("CategoryClick", "Clicked on: $categories")
                    }
                )
            }
            item { NewArrivals() }
            item { PopularProductsSection(navController,productsViewModel) }
        }

        BottomNavBar(
            selectedRoute = "home",
            onItemSelected = { navController.navigate(it) },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun TopBar(navController: NavController,cartViewModel: CartViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 70.dp, start = 16.dp, end = 16.dp,bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search product") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(30.dp)
        )

        Spacer(modifier = Modifier.width(15.dp))

        IconButton(onClick = {
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("cartItems", ArrayList(cartViewModel.cartItems.value)) // ✅ Ensure it's an ArrayList

            navController.navigate(Screen.Cart.route)
        }) {
            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
        }
    }
}

@Composable
fun CashbackBanner() {
    Spacer(modifier = Modifier.height(25.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0XFF900C27), RoundedCornerShape(16.dp))
            .padding(24.dp)
            .height(30.dp)
    ) {
        Text(
            "View our Flash Deals",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.clickable {
                // Handle click action
            }
        )
    }
}

@Composable
fun CategoryList(categories: List<String>, onCategoryClick: (String) -> Unit) {
    Spacer(modifier = Modifier.height(15.dp))
    Text(
        "Categories",
        modifier = Modifier.padding(start = 16.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(categories) { category ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2D336B))
                    .clickable { onCategoryClick(category) }
                    .padding(16.dp)
            ) {
                Text(
                    text = category,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun CategoryItem(title: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = title
        )
        Text(text = title)
    }
}

@Composable
fun NewArrivals() {
    Column {
        Text(
            text = "New items for you",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(listOf("Fashion", "Refreshments", "Sports", "Gadgets")) { category ->
                SpecialItem(category)
            }
        }
    }
}

@Composable
fun SpecialItem(title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.LightGray, RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, fontWeight = FontWeight.Bold)
    }
}

// CHANGED: This function now displays products from Firestore
@Composable
fun PopularProductsSection(navController: NavController, productsViewModel: ProductsViewModel) {
    // Observe products from Firestore
    val products by productsViewModel.products.collectAsState()

    Text(
        "Available Product",
        modifier = Modifier.padding(16.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    if (products.isEmpty()) {
        // If Firestore is empty or still loading, show placeholders
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ProductItemPlaceholder("Wireless Controller", "Ksh.9000")
            ProductItemPlaceholder("Nike Sport White", "Ksh.4500")
            ProductItemPlaceholder("Gloves XC", "Ksh.3000")
        }
    } else {
        // Render real products using a LazyRow
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(products) { product ->
                ProductItem(product, navController)
            }
        }
    }
}

// Placeholder composable for loading state (no image)
@Composable
fun ProductItemPlaceholder(title: String, price: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, RoundedCornerShape(16.dp))
        )
        Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(price, color = Color.Red, fontSize = 14.sp)
    }
}

// Product item composable that loads images and navigates correctly
@Composable
fun ProductItem(product: Product, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            navController.currentBackStackEntry?.savedStateHandle?.set("product", product) // ✅ Pass full product object
            navController.navigate(Screen.Product.route) // ✅ Navigate to Product Screen
        }
    ) {
        val painter = rememberAsyncImagePainter(product.imageUrl) // Coil
        Image(
            painter = painter,
            contentDescription = product.name,
            modifier = Modifier
                .size(170.dp)
                .clip(RoundedCornerShape(30.dp))
        )
        Text(product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text("Ksh.${product.price}", color = Color.Black, fontSize = 14.sp)
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}