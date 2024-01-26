package com.simplyfi.sdk.clients

import com.simplyfi.sdk.ClientConfig
import com.simplyfi.sdk.models.core.RunnableResult
import com.simplyfi.sdk.models.core.routines.Routine
import com.simplyfi.sdk.models.core.routines.RoutineExecute
import love.forte.plugin.suspendtrans.annotation.JsPromise

class RoutinesClient(config: ClientConfig) {
    private val httpClient = HttpClient(config, "v1/routines/")

    @JsPromise
    suspend fun get(name: String, token: String? = null): Routine {
        return httpClient.get(name, token)
    }

    @JsPromise
    suspend fun execute(
        name: String,
        payload: RoutineExecute,
        token: String? = null
    ): RunnableResult {
        return httpClient.post("$name/execute", payload, token)
    }

    suspend fun execute(
        name: String,
        vararg params: String,
        token: String? = null
    ): RunnableResult = execute(name, RoutineExecute(params), token)
}