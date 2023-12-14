package com.simplyfi.sdk.clients

import com.simplyfi.sdk.Config
import io.ktor.client.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.*

actual fun httpClient(config: Config, clientConfig: HttpClientConfig<*>.()-> Unit): HttpClient = HttpClient(Js, clientConfig)