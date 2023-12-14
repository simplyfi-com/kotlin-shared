package com.simplyfi.sample.ui

import androidx.compose.runtime.Composable
import com.simplyfi.sample.models.Route
import com.simplyfi.sdk.Config
import kotlin.random.Random

@Composable
expect fun Registration(config: Config, callback: (String, Route) -> Unit)

val letters = "abcdefghijklmnopqrstuvwxyz".split("")
val digits = "0123456789".split("")
val symbols = "!$#&".split("")

fun randomString(len: Long, pool: List<String>): String =
    (0..len).joinToString("") { Random.nextInt(0, pool.size).let { pool[it] } }