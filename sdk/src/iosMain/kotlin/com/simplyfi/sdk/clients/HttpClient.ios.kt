package com.simplyfi.sdk.clients

import com.simplyfi.sdk.Config
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

actual fun httpClient(config: Config, clientConfig: HttpClientConfig<*>.() -> Unit): HttpClient =
    HttpClient(Darwin)