package com.simplyfi.sdk.clients

import com.simplyfi.sdk.ClientConfig
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

internal actual fun httpClient(config: ClientConfig, clientConfig: HttpClientConfig<*>.() -> Unit): HttpClient =
    HttpClient(Darwin, clientConfig)