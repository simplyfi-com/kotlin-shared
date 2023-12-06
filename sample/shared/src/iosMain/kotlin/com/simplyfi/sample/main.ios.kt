package com.simplyfi.sample

import androidx.compose.ui.window.ComposeUIViewController
import com.simplyfi.sdk.Config
import com.simplyfi.sample.MR
import com.simplyfi.sdk.view.View
import dev.icerock.moko.resources.desc.desc

fun MainViewController() = ComposeUIViewController {
    App(
        Config(MR.strings.API_URL.desc().localized(), MR.strings.API_KEY.desc().localized()),
        MR.strings.GO_URL.desc().localized()
    ) {
        View(it)
    }
}