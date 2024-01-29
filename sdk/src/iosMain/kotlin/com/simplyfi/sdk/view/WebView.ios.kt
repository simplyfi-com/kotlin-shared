package com.simplyfi.sdk.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSMutableURLRequest
import platform.Foundation.NSURL
import platform.WebKit.WKUserScript
import platform.WebKit.WKUserScriptInjectionTime
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.javaScriptEnabled

fun WebViewController(config: ViewConfig) = ComposeUIViewController { WebView(config) }

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun WebView(
    config: ViewConfig,
    modifier: Modifier,
    onCreated: () -> Unit,
    onDispose: () -> Unit,
) {
    UIKitView(
        factory = {
            val userScript = WKUserScript(
                """localStorage.setItem('${config.tokenKey}', '"${config.token}"')""",
                WKUserScriptInjectionTime.WKUserScriptInjectionTimeAtDocumentStart,
                true
            )

            val wkWebViewConfiguration =
                WKWebViewConfiguration().apply {
                    allowsInlineMediaPlayback = true
                    defaultWebpagePreferences.allowsContentJavaScript = true
                    preferences.apply {
                        javaScriptEnabled = true
                    }
                    userContentController.addUserScript(userScript)
                }
            WKWebView(
                frame = CGRectZero.readValue(),
                configuration = wkWebViewConfiguration,
            ).apply {
                userInteractionEnabled = true
                allowsBackForwardNavigationGestures = true
            }.also {
                val request =
                    NSMutableURLRequest.requestWithURL(
                        URL = NSURL(string = config.url),
                    )
                it.loadRequest(request)
                onCreated()
            }
        },
        modifier = modifier,
        onRelease = {
            onDispose()
        },
    )
}