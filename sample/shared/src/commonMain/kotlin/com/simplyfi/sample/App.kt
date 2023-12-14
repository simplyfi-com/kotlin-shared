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
import dev.icerock.moko.resources.compose.stringResource

fun noop(route: Route) {}

@Composable
fun App(routeChange: (route: Route) -> Unit = { noop(it) }) {
    var token by rememberSaveable { mutableStateOf("") }
    var route by rememberSaveable { mutableStateOf(Route.Register) }
    val apiUrl = stringResource(MR.strings.API_URL)
    val apiKey = stringResource(MR.strings.API_KEY)
    val goUrl = stringResource(MR.strings.GO_URL)

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
                    Config(goUrl, token, viewId = 123)
                )
            }
        }
    }
}