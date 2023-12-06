package com.simplyfi.sample

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.simplyfi.sdk.view.View
import com.simplyfi.sdk.Config

@Composable fun MainView() = App(
    Config(stringResource(MR.strings.API_URL.resourceId), stringResource(MR.strings.API_KEY.resourceId)),
    stringResource(MR.strings.GO_URL.resourceId)
) {
    View(it)
}