package com.simplyfi.sdk.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun View(
    config: Config,
    modifier: Modifier,
    onCreated: () -> Unit,
    onDispose: () -> Unit,
) {
    AndroidView(
        factory = {
            WebView(it).apply {
                settings.apply {
                    javaScriptEnabled = true
                    allowFileAccess = true
                    domStorageEnabled = true
                }
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        view?.evaluateJavascript(
                            """localStorage.setItem('${config.tokenKey}', '"${config.token}"')""",
                            null
                        )
                    }
                }
                loadUrl(config.url)
            }
        },
        modifier,
        onRelease = {
            onDispose()
        },
    )
}