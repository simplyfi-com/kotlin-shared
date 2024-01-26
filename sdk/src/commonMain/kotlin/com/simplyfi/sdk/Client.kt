package com.simplyfi.sdk

import com.simplyfi.sdk.clients.RoutinesClient
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
class Client(config: ClientConfig) {
    val routines = RoutinesClient(config)
}