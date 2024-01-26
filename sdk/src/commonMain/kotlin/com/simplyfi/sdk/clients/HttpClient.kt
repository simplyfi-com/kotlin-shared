package com.simplyfi.sdk.clients

import com.simplyfi.sdk.ClientConfig
import com.simplyfi.sdk.models.core.ApiException
import io.ktor.client.*
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

internal expect fun httpClient(config: ClientConfig, clientConfig: HttpClientConfig<*>.()-> Unit={}): HttpClient

internal class HttpClient(config: ClientConfig, path: String) {
    @OptIn(ExperimentalSerializationApi::class)
    val client = httpClient(config) {
        install(HttpTimeout) {
            requestTimeoutMillis = config.timeout
        }
        defaultRequest {
            url {
                this.takeFrom(config.baseUrl)
                appendPathSegments(path)
            }
            headers {
                append("X-API-Key", config.apiKey)
            }
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                namingStrategy = JsonNamingStrategy.SnakeCase
                ignoreUnknownKeys = true
            })
        }

        HttpResponseValidator {
            validateResponse { response: HttpResponse ->
                val statusCode = response.status.value

                if (statusCode > 299) {
                    throw response.body<ApiException>()
                }
            }

            handleResponseExceptionWithRequest { cause: Throwable, _ ->
                throw cause
            }
        }
    }

    suspend inline fun <reified T, reified R> post(path: String, payload: T, token: String? = null): R =
        client.post {
            build(this, path, payload, token)
        }.body()

    suspend inline fun <reified T, reified R> put(path: String, payload: T, token: String? = null): R =
        client.put {
            build(this, path, payload, token)
        }.body()

    suspend inline fun <reified T, reified R> patch(path: String, payload: T, token: String? = null): R =
        client.patch {
            build(this, path, payload, token)
        }.body()

    suspend inline fun <reified R> get(path: String, token: String? = null): R =
        client.get {
            build(this, path, token)
        }.body()

    suspend inline fun <reified R> delete(path: String, token: String? = null): R =
        client.delete {
            build(this, path, token)
        }.body()

    internal inline fun <reified T> build(builder: HttpRequestBuilder, path: String, payload: T, token: String? = null) =
        build(builder, path, token) {
            if (payload != null) {
                setBody(payload)
            }
        }

    internal inline fun build(builder: HttpRequestBuilder, path: String, token: String? = null, callback: HttpRequestBuilder.() -> Unit) {
        builder.apply {
            url { appendPathSegments(path) }
            if (token != null) {
                io.ktor.http.headers {
                    append("Authorization", "Bearer $token")
                }
            }
            callback()
        }
    }
}
