package com.example.gradgoods.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gradgoods.screens.SignInScreen
import com.example.gradgoods.screens.SignUpScreen
import com.example.gradgoods.ui.screens.OnboardingScreen
import com.example.gradgoods.model.AuthViewModel
import com.example.gradgoods.model.CartViewModel
import com.example.gradgoods.screens.HomeScreen
import com.example.gradgoods.screens.ProductScreen
import com.example.gradgoods.screens.ProfileScreen
import com.example.gradgoods.model.Product
import com.example.gradgoods.model.ProductsViewModel
import com.example.gradgoods.model.MpesaViewModel
import com.example.gradgoods.screens.CartScreen
import com.google.firebase.auth.FirebaseAuth

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object SignIn : Screen("signin")
    object SignUp : Screen("signup")
    object Home : Screen("home") // ✅ Added Home route placeholder for navigation after sign-in
    object Profile : Screen("profile")
    object Product : Screen("product")
    object Cart : Screen("cart")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val productsViewModel: ProductsViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val mpesaViewModel: MpesaViewModel = viewModel()

    //change it later to Screen.Onboarding.route

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
            HomeScreen(navController, productsViewModel, cartViewModel)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController, auth = FirebaseAuth.getInstance())
        }
        composable(Screen.Product.route) { backStackEntry ->
            val product =
                navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")

            if (product != null) {
                ProductScreen(navController, product, cartViewModel)
            }
        }
        composable(Screen.Cart.route) {
            CartScreen(navController, cartViewModel, mpesaViewModel)
        }
    }
}