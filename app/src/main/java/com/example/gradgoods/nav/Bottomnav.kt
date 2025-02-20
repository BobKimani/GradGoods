package com.example.gradgoods.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

sealed class BottomNavItem(
    val title: String,
    val icon: @Composable () -> Unit,
    val route: String
) {
    object Home : BottomNavItem("Home", { Icon(Icons.Filled.Home, contentDescription = "Home") }, "home")
    object Cart : BottomNavItem("Cart", { Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart") }, "cart")
    object Profile : BottomNavItem("Profile", { Icon(Icons.Filled.Person, contentDescription = "Profile") }, "profile")
}

@Composable
fun BottomNavBar(selectedRoute: String, onItemSelected: (String) -> Unit) {
    NavigationBar(containerColor = Color.LightGray, contentColor = Color.White) {
        val items = listOf(BottomNavItem.Home, BottomNavItem.Cart, BottomNavItem.Profile)

        items.forEach { item ->
            NavigationBarItem(
                icon = item.icon,
                label = { Text(text = item.title) },
                selected = selectedRoute == item.route,
                onClick = { onItemSelected(item.route) }
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavBar(selectedRoute = "home") {}
}