package com.simplyfi.sdk.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebView(
    config: ViewConfig,
    modifier: Modifier = Modifier,
    onCreated: () -> Unit = {},
    onDispose: () -> Unit = {},
)