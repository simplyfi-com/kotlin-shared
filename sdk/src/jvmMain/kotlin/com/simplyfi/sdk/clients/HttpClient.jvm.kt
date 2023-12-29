package com.simplyfi.sdk.clients

import com.simplyfi.sdk.ClientConfig
import io.ktor.client.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

internal actual fun httpClient(config: ClientConfig, clientConfig: HttpClientConfig<*>.()-> Unit): HttpClient =
    HttpClient(OkHttp, clientConfig)