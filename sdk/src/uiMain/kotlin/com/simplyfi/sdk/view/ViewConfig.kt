package com.simplyfi.sdk.view

enum class ViewStrategy {
    EMBED, IFRAME,
}

data class ViewConfig(
    val url: String,
    val token: String,
    val strategy: ViewStrategy = ViewStrategy.EMBED,
    val viewId: Int? = null,
    val tokenKey: String = "sf.go.access",
    val captureBackPress: Boolean = true,
)
