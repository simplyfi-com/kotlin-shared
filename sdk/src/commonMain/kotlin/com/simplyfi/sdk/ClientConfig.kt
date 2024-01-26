package com.simplyfi.sdk

import kotlin.js.JsExport

@JsExport
data class ClientConfig(val baseUrl: String, val apiKey: String, var timeout: Long = 180_000)
