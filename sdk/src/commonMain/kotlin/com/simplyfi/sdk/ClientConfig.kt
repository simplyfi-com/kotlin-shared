package com.simplyfi.sdk

data class ClientConfig(val baseUrl: String, val apiKey: String, var timeout: Long = 180_000)
