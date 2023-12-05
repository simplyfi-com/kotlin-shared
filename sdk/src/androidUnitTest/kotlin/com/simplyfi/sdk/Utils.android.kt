package com.simplyfi.sdk

import androidx.compose.ui.res.stringResource

internal actual fun getEnv(name: String): String? =
    when (name) {
        "API_KEY" -> stringResource(R.string.API_KEY)
        "API_URL" -> stringResource(R.string.API_URL)
        else -> ""
    }
