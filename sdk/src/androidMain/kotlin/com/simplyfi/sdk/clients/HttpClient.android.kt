package com.simplyfi.sdk.clients

import com.simplyfi.sdk.Config
import io.ktor.client.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual fun httpClient(config: Config, clientConfig: HttpClientConfig<*>.()-> Unit): HttpClient =
    HttpClient(OkHttp, clientConfig)