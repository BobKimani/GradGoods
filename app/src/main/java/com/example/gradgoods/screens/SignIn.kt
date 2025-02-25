package com.example.gradgoods.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gradgoods.R
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gradgoods.model.AuthViewModel
import com.example.gradgoods.nav.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val user by viewModel.user.collectAsState() // ✅ Observe user state from AuthViewModel

    // ✅ Navigate to the home screen if the user is authenticated
    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.SignIn.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White) // Soft Pink Background
            .padding(top = 50.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 100.dp))

        Text(
            "Welcome Back",
            fontSize = 30.sp,
            color = Color(0xFF2D336B), // Deep Navy
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            "Sign in with your email or password",
            fontSize = 22.sp,
            color = Color(0xFF2D336B) // Deep Navy
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

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { viewModel.signIn(email, password) }, // ✅ Trigger Firebase sign-in
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D336B)), // Deep Navy

        ) {
            Text("Continue",
                fontSize = 22.sp,
                color = Color.White)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            "-----Or Sign In with------",
            fontSize = 20.sp,
            color = Color(0xFF2D336B) // Deep Navy
        )

        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = "Sign in with Google",
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(35.dp))

        Row {
            Text(
                "Don't have an account? ",
                fontSize = 20.sp,
                color = Color(0xFF2D336B), // Deep Navy
                fontWeight = FontWeight.Bold
            )
            Text(
                "Sign Up",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = Color(0XFF900C27),
                modifier = Modifier.clickable { navController.navigate(Screen.SignUp.route) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    SignInScreen(rememberNavController(), viewModel = AuthViewModel())
}