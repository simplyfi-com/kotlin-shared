package com.simplyfi.sdk.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import com.multiplatform.webview.web.IOSWebView
import com.multiplatform.webview.web.WebContent
import com.multiplatform.webview.web.rememberWebViewState
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.setValue
import platform.WebKit.WKUserScript
import platform.WebKit.WKUserScriptInjectionTime
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.javaScriptEnabled

@OptIn(ExperimentalForeignApi::class)
@Composable
fun View(
    config: Config,
    modifier: Modifier = Modifier,
    onCreated: () -> Unit = {},
    onDispose: () -> Unit = {},
) {
    val state = rememberWebViewState(config.url)

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
                    defaultWebpagePreferences.allowsContentJavaScript = state.webSettings.isJavaScriptEnabled
                    preferences.apply {
                        setValue(state.webSettings.allowFileAccessFromFileURLs, forKey = "allowFileAccessFromFileURLs")
                        javaScriptEnabled = state.webSettings.isJavaScriptEnabled
                    }
                    setValue(state.webSettings.allowUniversalAccessFromFileURLs, forKey = "allowUniversalAccessFromFileURLs")
                    userContentController.addUserScript(userScript)
                }
            WKWebView(
                frame = CGRectZero.readValue(),
                configuration = wkWebViewConfiguration,
            ).apply {
                userInteractionEnabled = true
                allowsBackForwardNavigationGestures = true
                customUserAgent = state.webSettings.customUserAgentString
            }.also {
                IOSWebView(it).loadUrl((state.content as WebContent.Url).url, (state.content as WebContent.Url).additionalHttpHeaders)
                onCreated()
            }
        },
        modifier = modifier,
        onRelease = {
            onDispose()
        },
    )
}