@file:OptIn(ExperimentalJsExport::class)

package com.simplyfi.sdk.view

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
enum class ViewStrategy {
    EMBED, IFRAME,
}

@JsExport
data class ViewConfig(
    val url: String,
    val token: String,
    val strategy: ViewStrategy = ViewStrategy.EMBED,
    val viewId: Int? = null,
    val tokenKey: String = "sf.go.access",
    val captureBackPress: Boolean = true,
)
