package com.simplyfi.sdk.clients

import com.simplyfi.sdk.ClientConfig
import io.ktor.client.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.*

internal actual fun httpClient(config: ClientConfig, clientConfig: HttpClientConfig<*>.()-> Unit): HttpClient = HttpClient(Js, clientConfig)