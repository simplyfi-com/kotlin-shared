package com.simplyfi.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.simplyfi.sample.models.Route
import com.simplyfi.sample.ui.*
import com.simplyfi.sdk.view.Config
import com.simplyfi.sdk.view.View
import com.simplyfi.sdk.view.ViewStrategy
import dev.icerock.moko.resources.compose.stringResource

val NoOpRouteChange: Route.() -> Unit = {}

@Composable
fun App(viewStrategy: ViewStrategy = ViewStrategy.EMBED, routeChange: Route.() -> Unit = NoOpRouteChange) {
    var token by rememberSaveable { mutableStateOf("") }
    var route by rememberSaveable { mutableStateOf(Route.Register) }
    val apiUrl = stringResource(MR.strings.API_URL)
    val apiKey = stringResource(MR.strings.API_KEY)
    val webUrl = stringResource(MR.strings.WEB_URL)

    LaunchedEffect(route) {
        routeChange(route)
    }

    SampleTheme {
        Navigation(route) {
            when (it) {
                Route.Register -> Registration(
                    com.simplyfi.sdk.Config(
                        apiUrl,
                        apiKey
                    )
                ) { t, r ->
                    token = t
                    route = r
                }

                Route.Onboarding -> View(
                    Config(webUrl, token, strategy = viewStrategy)
                )
            }
        }
    }
}