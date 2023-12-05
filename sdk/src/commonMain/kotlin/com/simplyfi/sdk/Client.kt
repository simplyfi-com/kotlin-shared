package com.simplyfi.sdk

import com.simplyfi.sdk.clients.RoutinesClient

class Client(private val config: Config) {
    val routines = RoutinesClient(config)
}