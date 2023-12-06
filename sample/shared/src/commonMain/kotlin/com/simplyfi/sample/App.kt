package com.simplyfi.sample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.simplyfi.sample.models.Route
import com.simplyfi.sample.ui.Navigation
import com.simplyfi.sample.ui.Registration
import com.simplyfi.sdk.view.Config
import com.simplyfi.sdk.Config as ApiConfig

@Composable
fun App(apiConfig: ApiConfig, url: String, onboarding: @Composable (config: Config) -> Unit) {
    var token by rememberSaveable { mutableStateOf("") }
    var route by rememberSaveable { mutableStateOf(Route.Register) }

    MaterialTheme {
        Surface {
            Navigation(route, Modifier.fillMaxSize()) {
                when (it) {
                    Route.Register -> Registration(apiConfig) { t, r ->
                        token = t
                        route = r
                    }
                    Route.Onboarding -> onboarding(Config(url, token))
                }
            }
        }
    }
}