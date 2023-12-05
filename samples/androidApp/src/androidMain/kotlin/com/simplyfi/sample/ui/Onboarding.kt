package com.simplyfi.sample.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.simplyfi.sample.R
import com.simplyfi.sdk.view.Config
import com.simplyfi.sdk.view.View

@Composable
fun Onboarding(token: String) {
    val config = Config(
        url = stringResource(R.string.GO_URL),
        token = token
    )

    View(
        Modifier.fillMaxSize(),
        config
    )
}