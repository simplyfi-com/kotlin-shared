package com.simplyfi.sdk

internal actual fun getEnv(name: String): String? =
    js("globalThis.process.env[name]") as String?