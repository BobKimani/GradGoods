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

    // ✅ Updated to navigate to HomeScreen on successful sign-up
    fun onContinue() {
        if (email.isNotBlank() && password == confirmPassword) {
            viewModel.signUp(email, password) {
                navController.navigate(Screen.Home.route) // Navigate to HomeScreen
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5E5F3))
            .padding(top = 50.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 100.dp))

        Text(
            "Register Account",
            fontSize = 30.sp,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            "Sign up with your email and password",
            fontSize = 20.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { onContinue() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Continue")
        }

        Spacer(modifier = Modifier.height(35.dp))

        Row {
            Text(
                "Already have an account? ",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Sign In",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFA020F0),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { navController.navigate(Screen.SignIn.route) }
            )
        }
    }
}

/***
@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
// Ensure proper preview setup here
}
 ***/