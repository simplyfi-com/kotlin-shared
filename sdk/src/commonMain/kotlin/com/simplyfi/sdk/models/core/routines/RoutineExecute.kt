package com.simplyfi.sdk.models.core.routines

import kotlinx.serialization.Serializable

@Serializable
data class RoutineExecute(val params: Array<out String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RoutineExecute

        return params.contentEquals(other.params)
    }

    override fun hashCode(): Int {
        return params.contentHashCode()
    }
}