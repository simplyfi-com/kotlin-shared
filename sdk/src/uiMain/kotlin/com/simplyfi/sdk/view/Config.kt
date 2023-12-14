package com.simplyfi.sdk.view

data class Config(val url: String, val token: String, val viewId: Int? = null, val tokenKey: String = "sf.go.access", val captureBackPress: Boolean = true)