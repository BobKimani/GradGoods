package com.example.gradgoods.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


import com.example.gradgoods.R
import com.example.gradgoods.nav.*
import com.google.firebase.auth.FirebaseAuth

val GradGoodsPurple = Color(0xFF6A1B9A)  // Vibrant Purple Theme Color
val DarkPurple = Color(0xFFF5E5F3)
val LightPurple = Color(0xFFCE93D8)

@Composable
fun ProfileScreen(navController: NavController, auth: FirebaseAuth) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkPurple)
            .padding(top = 50.dp)
    ) {
        Column( // Changed from LazyColumn to Column
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Added vertical scroll
                .padding(bottom = 150.dp) // To avoid overlapping with BottomNavBar
        ) {
            // Ensure user details are refreshed
            auth.currentUser?.reload() // Added to refresh user details

            val currentUser = auth.currentUser
            val userName = currentUser?.displayName ?: "Guest"
            val userEmail = currentUser?.email ?: "No email available"

            // Top Profile Info
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkPurple, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_placeholder),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(50.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = userName, fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.ExtraBold)
                        Text(text = userEmail, fontSize = 20.sp, color = Color.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Account & Settings Options
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeader("Account")
                ProfileOption("Member", Icons.Filled.Person)
                ProfileOption("Change Password", Icons.Filled.Lock)

                Spacer(modifier = Modifier.height(24.dp))

                SectionHeader("General")
                ProfileOption("Notifications", Icons.Default.Notifications)
                ProfileOption("Language", Icons.Filled.Language)
                ProfileOption("Country", Icons.Filled.Public)
                ProfileOption("Clear Cache", Icons.Filled.Delete)

                Spacer(modifier = Modifier.height(24.dp))

                SectionHeader("More")
                ProfileOption("Legal and Policies", Icons.Filled.Gavel)
                ProfileOption("Help & Feedback", Icons.Filled.Help)
                ProfileOption("About Us", Icons.Filled.Info)

                Spacer(modifier = Modifier.height(35.dp))

                // Logout Button
                Button(
                    onClick = { auth.signOut() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GradGoodsPurple),

                ) {
                    Text("Log Out", color = Color.Black, fontSize = 20.sp)
                }
            }
        }

        // Bottom Navigation Bar (Inside Box to stay at the bottom)
        BottomNavBar(
            selectedRoute = "profile",
            onItemSelected = { navController.navigate(it) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}
// Section Header
@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

// Profile Option Item
@Composable
fun ProfileOption(label: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {},
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = GradGoodsPurple, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = label, fontSize = 18.sp, color = Color.Black)
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun ProfilePreview(){
//    ProfileScreen(auth = FirebaseAuth.getInstance(), rememberNavController())
//}