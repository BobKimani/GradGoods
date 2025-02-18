package com.example.gradgoods.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.example.gradgoods.R
import com.example.gradgoods.nav.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController ) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5E5F3))) {
        TopBar(navController)
        CashbackBanner()
//        CategorySection()
        SpecialForYouSection()
        PopularProductsSection()
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
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search product") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        IconButton(onClick = { /*navController.navigate(Screen.Cart.route) */ }) {
            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
        }

        Box(modifier = Modifier.size(24.dp)) {
            Text("3", color = Color.White, fontSize = 12.sp, modifier = Modifier
                .background(Color.Red, CircleShape)
                .align(Alignment.TopEnd)
                .padding(4.dp))
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
    ) {
        Text("A Spring Surprise\nCashback 25%", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
    }
}

//@Composable
//fun CategorySection() {
//    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
//        CategoryItem("Flash Deal", R.drawable.ic_flash)
//        CategoryItem("Bill", R.drawable.ic_bill)
//        CategoryItem("Game", R.drawable.ic_game)
//        CategoryItem("Daily Gift", R.drawable.ic_gift)
//        CategoryItem("More", R.drawable.ic_more)
//    }
//}

@Composable
fun CategoryItem(title: String, iconRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painterResource(id = iconRes), contentDescription = title)
        Text(title, fontSize = 14.sp)
    }
}

@Composable
fun SpecialForYouSection() {
    Text("Special for you", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        SpecialItem("Fashion", "85 Brands")
        SpecialItem("Mobile Phone", "15 Brands")
    }
}

@Composable
fun SpecialItem(title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(150.dp).background(Color.LightGray, RoundedCornerShape(16.dp)))
        Text(title, fontWeight = FontWeight.Bold)
        Text(subtitle, fontSize = 12.sp)
    }
}

@Composable
fun PopularProductsSection() {
    Text("Popular Product", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        ProductItem("Wireless Controller", "$79.99")
        ProductItem("Nike Sport White", "$49.99")
        ProductItem("Gloves XC", "$36.55")
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

//@Preview
//@Composable
//fun HomeScreenPreview(){
//    HomeScreen()
//
//}