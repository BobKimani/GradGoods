package com.example.gradgoods.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gradgoods.screens.SignInScreen
import com.example.gradgoods.screens.SignUpScreen
import com.example.gradgoods.ui.screens.OnboardingScreen
import com.example.gradgoods.auth.AuthViewModel
import com.example.gradgoods.screens.HomeScreen

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object SignIn : Screen("signin")
    object SignUp : Screen("signup")
    object Home : Screen("home") // ✅ Added Home route placeholder for navigation after sign-in
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    // ✅ Initialize AuthViewModel once and share across screens
    val authViewModel: AuthViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Onboarding.route) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(onContinue = {
                navController.navigate(Screen.SignIn.route)
            })
        }
        composable(Screen.SignIn.route) {
            SignInScreen(navController, authViewModel)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController, authViewModel)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }


    }
}