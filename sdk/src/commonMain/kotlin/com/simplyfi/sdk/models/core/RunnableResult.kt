package com.simplyfi.sdk.models.core

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
@Serializable
data class RunnableResult(
    val subject: String? = null,
    val message: String? = null,
    val errorCode: String? = null,
    val errorMessage: String? = null
)
