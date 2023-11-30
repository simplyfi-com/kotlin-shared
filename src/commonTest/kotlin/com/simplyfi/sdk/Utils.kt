package com.simplyfi.sdk

import kotlin.random.Random

internal expect fun getEnv(name: String): String?

val letters = "abcdefghijklmnopqrstuvwxyz".split("")
val digits = "0123456789".split("")
val symbols = "!$#&".split("")

fun randomString(len: Long, pool: List<String>): String =
    (0..len).joinToString("") { Random.nextInt(0, pool.size).let { pool[it] } }