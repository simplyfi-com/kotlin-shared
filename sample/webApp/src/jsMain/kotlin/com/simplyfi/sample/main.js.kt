package com.simplyfi.sample

import com.simplyfi.sample.models.Route
import kotlinx.browser.window
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable("root") {
        App {
            when (it) {
                Route.Register -> {
                    window.history.pushState(null, "Register", "/register")
                }
                Route.Onboarding -> {
                    window.history.pushState(null, "Onboarding", "/onboarding")
                }
            }
        }
    }
}