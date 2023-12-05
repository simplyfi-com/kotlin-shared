package com.simplyfi.sample.models

sealed class Routes(val route: String) {
    data object Register : Routes("Register")
    data object Onboarding : Routes("Onboarding")
}