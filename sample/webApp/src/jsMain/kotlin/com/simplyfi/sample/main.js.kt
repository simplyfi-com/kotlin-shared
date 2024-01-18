package com.simplyfi.sample

import com.simplyfi.sample.ui.Registration
import com.simplyfi.sdk.ClientConfig
import dev.icerock.moko.resources.compose.stringResource
import org.jetbrains.compose.web.renderComposable

@OptIn(ExperimentalJsExport::class)
@JsExport
fun RegistrationComponent(rootElementId: String, callback: (token: String, webUrl: String) -> Unit) {
    renderComposable(rootElementId) {
        val apiUrl = stringResource(MR.strings.API_URL)
        val apiKey = stringResource(MR.strings.API_KEY)
        val webUrl = stringResource(MR.strings.WEB_URL)

        Registration(
            ClientConfig(
                apiUrl,
                apiKey
            )
        ) { t, _ ->
            callback(t, webUrl)
        }
    }
}