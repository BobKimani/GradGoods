package com.example.gradgoods.nav

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

sealed class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    object Home : BottomNavItem("Home", Icons.Filled.Home, "home")
    object Cart : BottomNavItem("Cart", Icons.Filled.ShoppingCart, "cart")
    object Profile : BottomNavItem("Profile", Icons.Filled.Person, "profile")
}

@Composable
fun BottomNavBar(selectedRoute: String, onItemSelected: (String) -> Unit, modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = Color.White,
        contentColor = Color(0xFF2D336B)
    ) {
        val items = listOf(BottomNavItem.Home, BottomNavItem.Cart, BottomNavItem.Profile)

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
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
    BottomNavBar(selectedRoute = "home", onItemSelected = {})
}