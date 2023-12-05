package com.simplyfi.sdk

internal actual fun getEnv(name: String): String? =
    System.getenv(name)