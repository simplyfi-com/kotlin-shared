package com.simplyfi.sdk

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
data class ClientConfig(val baseUrl: String, val apiKey: String, var timeout: Long = 180_000)
