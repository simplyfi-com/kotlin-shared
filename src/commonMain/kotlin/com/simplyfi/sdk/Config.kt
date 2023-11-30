package com.simplyfi.sdk

data class Config(val baseUrl: String, val apiKey: String, val timeout: Long = 180_000)
