package com.simplyfi.sdk.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun View(
    config: Config,
    modifier: Modifier = Modifier,
    onCreated: () -> Unit = {},
    onDispose: () -> Unit = {},
)