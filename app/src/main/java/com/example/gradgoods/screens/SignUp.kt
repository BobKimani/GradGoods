package com.example.gradgoods.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gradgoods.model.AuthViewModel
import com.example.gradgoods.nav.Screen

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SignUpScreen(navController: NavController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val user by viewModel.user.collectAsStateWithLifecycle()

    fun onContinue() {
        if (email.isNotBlank() && password == confirmPassword) {
            viewModel.signUp(email, password) {
                navController.navigate(Screen.Home.route)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White) // Changed to vibrant purple theme
            .padding(top = 50.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 100.dp))

        Text(
            "Register Account",
            fontSize = 30.sp,
            color = Color(0xFF2D336B),
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            "Sign up with your email and password",
            fontSize = 22.sp,
            color = Color(0xFF2D336B)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color(0xFF2D336B)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF7886C7), // Soft Blue
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color(0xFF2D336B)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF7886C7), // Soft Blue
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password", color = Color(0xFF2D336B)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF7886C7), // Soft Blue
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { onContinue() },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D336B)) // Purple button
        ) {
            Text("Continue",
                fontSize = 22.sp,
                color = Color.White)
        }

        Spacer(modifier = Modifier.height(35.dp))

        Row {
            Text(
                "Already have an account? ",
                fontSize = 20.sp,
                color = Color(0xFF2D336B),
                fontWeight = FontWeight.Bold
            )

            Text(
                "Sign In",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0XFF900C27), // Gold text for contrast
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { navController.navigate(Screen.SignIn.route) }
            )
        }
    }
}