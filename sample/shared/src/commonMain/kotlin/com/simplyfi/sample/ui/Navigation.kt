package com.simplyfi.sample.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import com.simplyfi.sample.models.Route

@Composable
fun Navigation(
    currentScreen: Route,
    content: @Composable (Route) -> Unit
) {
    val saveableStateHolder = rememberSaveableStateHolder()

    saveableStateHolder.SaveableStateProvider(currentScreen) {
        content(currentScreen)
    }
}