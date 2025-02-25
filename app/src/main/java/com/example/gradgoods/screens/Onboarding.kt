package com.example.gradgoods.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.example.gradgoods.R
import com.example.gradgoods.nav.Screen

@Composable
fun OnboardingScreen(onContinue: () -> Unit) {
    val pages = listOf(
        OnboardingPage("GradGoods", "Welcome to GradGoods. Let's Shop!", R.drawable.onboard),
        OnboardingPage("GradGoods", "We are at your convenience", R.drawable.onboard2)
    )
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalPager(
            state = pagerState,
            count = pages.size,
            modifier = Modifier.weight(1f) // Allows space for button & indicator
        ) { page ->
            OnboardingPageUI(pages[page])
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color(0xFF2D336B), // Deep Navy
                inactiveColor = Color.Gray,
                modifier = Modifier.padding(8.dp) // Ensures visibility
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    coroutineScope.launch(Dispatchers.Main) {
                        if (pagerState.currentPage < pages.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            onContinue()
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp), // Rounded button
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D336B)), // Deep Navy
                modifier = Modifier
                    .fillMaxWidth(0.9f) // Adjusted button width
                    .height(70.dp)
                    .padding(bottom = 16.dp) // Adds space for better visibility
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.size - 1) "Get Started" else "Continue",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Ensures good contrast
                )
            }
        }
    }
}

@Composable
fun OnboardingPageUI(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = page.title,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF2D336B), // Deep Navy
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = page.description,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF7886C7) // Soft Blue
        )
        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = page.image),
            contentDescription = "Onboarding Image",
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .height(250.dp)
        )
    }
}

data class OnboardingPage(val title: String, val description: String, val image: Int)

/**
@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
OnboardingScreen()
}
 **/