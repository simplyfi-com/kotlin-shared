package com.simplyfi.sdk.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState

@Composable
fun View(
    modifier: Modifier,
    config: Config,
    onCreated: () -> Unit = {},
    onDispose: () -> Unit = {},
) {
    val state = rememberWebViewState(config.url)
    state.webSettings.androidWebSettings.apply {
        this.domStorageEnabled = true
        this.allowFileAccess = true
    }
    val navigator = rememberWebViewNavigator()

    WebView(
        state,
        modifier,
        config.captureBackPress,
        navigator,
        onCreated = {
            navigator.evaluateJavaScript(
                """localStorage.setItem('${config.tokenKey}', '"${config.token}"')"""
            )
            onCreated()
        },
        onDispose
    )
}