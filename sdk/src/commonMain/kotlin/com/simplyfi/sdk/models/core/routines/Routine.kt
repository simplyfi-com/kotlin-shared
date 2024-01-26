package com.simplyfi.sdk.models.core.routines

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
data class Routine(val name: String, val comment: String, val params: Array<Param>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Routine

        if (name != other.name) return false
        if (comment != other.comment) return false

        return params.contentEquals(other.params)
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + params.contentHashCode()
        return result
    }
}
