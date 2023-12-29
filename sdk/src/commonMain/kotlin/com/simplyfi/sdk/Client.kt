package com.simplyfi.sdk

import com.simplyfi.sdk.clients.RoutinesClient

class Client(config: ClientConfig) {
    val routines = RoutinesClient(config)
}