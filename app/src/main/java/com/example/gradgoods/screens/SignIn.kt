package com.example.gradgoods.ui.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController
import com.example.gradgoods.nav.Screen
import com.example.gradgoods.R

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SignInScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5E5F3))
            .padding(top = 50.dp, start = 15.dp,end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top= 100.dp))

        Text(
            "Welcome Back",
            fontSize = 30.sp,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer (modifier = Modifier.height(30.dp))

        Text(
            "Sign in with your email or password",
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

        Spacer(modifier= Modifier.height(50.dp))

        Button(
            onClick = { /* Handle sign-in */ },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Continue")
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            "-----Or Sign In with------",
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = "Sign in with Google",
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(35.dp))

        Row{
            Text(
                "Dont have an account? ",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight= FontWeight.Bold
            )
            Text(
                "Sign Up",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFA020F0),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {navController.navigate(Screen.SignUp.route) }
            )
        }
    }
}

/***
@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    SignInScreen()
} **/