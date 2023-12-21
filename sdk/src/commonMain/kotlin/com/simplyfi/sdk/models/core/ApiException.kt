package com.simplyfi.sdk.models.core

import kotlinx.serialization.Serializable

@Serializable
data class ApiException(val errorCode: String, val errorMessage: String, val requestId: String) : Throwable()