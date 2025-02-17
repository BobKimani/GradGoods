package com.example.gradgoods

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.gradgoods.nav.AppNavGraph
import com.example.gradgoods.ui.screens.OnboardingScreen
import com.example.gradgoods.ui.theme.GradGoodsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GradGoodsTheme {
                AppNavGraph()
            }
        }
    }
}