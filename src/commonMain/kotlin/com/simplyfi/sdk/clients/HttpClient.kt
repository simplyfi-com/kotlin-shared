package com.simplyfi.sdk.clients

import com.simplyfi.sdk.Config
import com.simplyfi.sdk.models.core.ApiException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.engine.cio.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

internal class HttpClient(config: Config, path: String) {
    @OptIn(ExperimentalSerializationApi::class)
    val client = HttpClient(CIO) {
        engine {
            requestTimeout = config.timeout
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
        request(HttpMethod.Post, path, payload, token)

    suspend inline fun <reified T, reified R> put(path: String, payload: T, token: String? = null): R =
        request(HttpMethod.Put, path, payload, token)

    suspend inline fun <reified T, reified R> patch(path: String, payload: T, token: String? = null): R =
        request(HttpMethod.Patch, path, payload, token)

    suspend inline fun <reified R> get(path: String, token: String? = null): R =
        request(HttpMethod.Get, path, token)

    suspend inline fun <reified R> delete(path: String, token: String? = null): R =
        request(HttpMethod.Delete, path, token)

    suspend inline fun <reified T, reified R> request(method: HttpMethod, path: String, payload: T, token: String? = null): R {
        val response = client.request {
            url { appendPathSegments(path) }
            this.method = method
            if (payload != null) {
                setBody(payload)
            }
            if (token != null) {
                headers {
                    append("Authorization", "Bearer $token")
                }
            }
        }

        return response.body()
    }

    suspend inline fun <reified R> request(method: HttpMethod, path: String, token: String? = null): R {
        return client.prepareRequest {
            url { appendPathSegments(path) }
            this.method = method
            if (token != null) {
                headers {
                    append("Authorization", "Bearer $token")
                }
            }
        }.body()
    }
}