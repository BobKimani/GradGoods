package com.example.gradgoods.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.gradgoods.R
import com.example.gradgoods.nav.Screen
import com.example.gradgoods.nav.BottomNavBar
import com.google.android.play.integrity.internal.f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController ) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5E5F3))) {
        TopBar(navController)
        CashbackBanner()
        SpecialForYouSection()
        PopularProductsSection()
        Spacer(modifier = Modifier.weight(1f))
        BottomNavBar(selectedRoute = "home", onItemSelected = { navController.navigate(it) })
    }
}

@Composable
fun TopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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

        IconButton(onClick = { /*navController.navigate(Screen.Cart.route) */ }) {
            Icon(
                Icons.Default.ShoppingCart,
                contentDescription = "Cart"
            )
        }
    }
}

@Composable
fun CashbackBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFA020F0), RoundedCornerShape(16.dp))
            .padding(24.dp)
            .height(30.dp)
    ) {
        Text(
            "View our Flash Deals" ,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .clickable { /* Handle click action */ }
        )
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
fun SpecialForYouSection() {
    Text("Special for you", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        SpecialItem("Fashion")
        SpecialItem("Refreshments")
    }
}

@Composable
fun SpecialItem(title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(150.dp).background(Color.LightGray, RoundedCornerShape(16.dp)))
        Text(title, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PopularProductsSection() {
    Text("Popular Product", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        ProductItem("Wireless Controller", "Ksh.9000")
        ProductItem("Nike Sport White", "Ksh.4500")
        ProductItem("Gloves XC", "Ksh. 3000")
    }
}

@Composable
fun ProductItem(title: String, price: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(120.dp).background(Color.LightGray, RoundedCornerShape(16.dp)))
        Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(price, color = Color.Red, fontSize = 14.sp)
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen(rememberNavController())
}