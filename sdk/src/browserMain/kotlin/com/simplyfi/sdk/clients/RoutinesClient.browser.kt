@file:OptIn(DelicateCoroutinesApi::class, ExperimentalJsExport::class)

package com.simplyfi.sdk.clients

import com.simplyfi.sdk.models.core.RunnableResult
import com.simplyfi.sdk.models.core.routines.Routine
import com.simplyfi.sdk.models.core.routines.RoutineExecute
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

@JsExport
fun RoutinesClient.getAsync(
    name: String,
    token: String? = null
): Promise<Routine> = GlobalScope.promise { get(name, token) }

@JsExport
fun RoutinesClient.executeAsync(
    name: String,
    payload: RoutineExecute,
    token: String? = null
): Promise<RunnableResult> = GlobalScope.promise { execute(name, payload, token) }