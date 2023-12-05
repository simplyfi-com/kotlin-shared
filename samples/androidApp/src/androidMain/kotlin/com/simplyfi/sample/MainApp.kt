package com.simplyfi.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simplyfi.sample.models.Routes
import com.simplyfi.sample.ui.Onboarding
import com.simplyfi.sample.ui.Registration

@Composable
fun MainApp() {
    val navController = rememberNavController()

    var token by rememberSaveable { mutableStateOf("") }

    NavHost(navController = navController, startDestination = Routes.Register.route) {
        composable(Routes.Register.route) {
            Registration(navController) {
                token = it
            }
        }

        composable(Routes.Onboarding.route) {
            Onboarding(token)
        }
    }
}